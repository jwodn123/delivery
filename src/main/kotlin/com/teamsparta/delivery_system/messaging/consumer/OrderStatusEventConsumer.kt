package com.teamsparta.delivery_system.messaging.consumer

import com.teamsparta.delivery_system.domain.dto.MessageDto
import com.teamsparta.delivery_system.domain.enums.OrderStatus
import com.teamsparta.delivery_system.exception.BadRequestException
import com.teamsparta.delivery_system.exception.NotFoundException
import com.teamsparta.delivery_system.repository.MemberRepository
import com.teamsparta.delivery_system.util.SmsUtil
import org.slf4j.LoggerFactory
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Service
import io.sentry.Sentry

@Service
class OrderStatusEventConsumer(
    private val smsUtil: SmsUtil,
    private val memberRepository: MemberRepository,
) {

    private val logger = LoggerFactory.getLogger(OrderStatusEventConsumer::class.java)

    @RabbitListener(queues = ["\${rabbitmq.queue.name.orderStatus}"])
    fun receiveOrderStatusMessage(messageDto: MessageDto) {
        try {
            logger.info("주문 상태 메시지 수신됨: {}", messageDto.toString())
            throw BadRequestException("지속적인 Consume Error 가 발생하고 있습니다.")
            sendMessageToCustomer(messageDto)
        } catch (e: Exception) {
            Sentry.setTag("RabbitMQ", "ConsumeFail")
            Sentry.captureException(e);
        }
    }


    /**
     * 주문 상태에 따라 메시지를 전송
     */
    fun sendMessageToCustomer(messageDto: MessageDto) {
        logger.info("Message Consumed !")
        val member = memberRepository.findById(messageDto.memberId).orElseThrow { NotFoundException("사용자를 찾을 수 없습니다.") }

        val statusMessage = when (messageDto.status) {
            OrderStatus.CONFIRMED -> "주문이 확정되었습니다."
            OrderStatus.DE_START -> "배달이 시작되었습니다."
            OrderStatus.DE_FINISH -> "배달이 완료되었습니다."
            else -> {
                logger.warn("Unhandled order status: ${messageDto.status}")
                return
            }
        }

        // SMS API 호출
        // smsUtil.sendOne(member.phone, statusMessage)
        logger.info("문자 발송 {}: {}", member.phone, statusMessage)
    }
}
