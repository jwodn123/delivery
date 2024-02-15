package com.teamsparta.delivery_system.messaging.consumer

import com.teamsparta.delivery_system.domain.dto.MessageDto
import com.teamsparta.delivery_system.domain.enums.OrderStatus
import com.teamsparta.delivery_system.exception.NotFoundException
import com.teamsparta.delivery_system.repository.MemberRepository
import com.teamsparta.delivery_system.util.SmsUtil
import org.slf4j.LoggerFactory
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Service

@Service
class OrderStatusEventConsumer(
    private val smsUtil: SmsUtil,
    private val memberRepository: MemberRepository
) {

    private val logger = LoggerFactory.getLogger(OrderStatusEventConsumer::class.java)

    @RabbitListener(queues = ["\${rabbitmq.queue.name}"])
    fun receiveOrderStatusMessage(messageDto: MessageDto) {
        logger.info("Received order status message: {}", messageDto.toString())
        sendMessageToCustomer(messageDto)
    }

    /**
     * 주문 상태에 따라 메시지를 전송
     */
    private fun sendMessageToCustomer(messageDto: MessageDto) {
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
        smsUtil.sendOne(member.phone, statusMessage)
    }
}
