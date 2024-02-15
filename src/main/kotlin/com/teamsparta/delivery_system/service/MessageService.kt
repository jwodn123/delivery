package com.teamsparta.delivery_system.service

import com.teamsparta.delivery_system.domain.dto.MessageDto
import com.teamsparta.delivery_system.domain.enums.OrderStatus
import org.slf4j.LoggerFactory
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class MessageService(
    private val rabbitTemplate: RabbitTemplate,

    @Value("\${rabbitmq.exchange.name}")
    private val exchangeName: String,

    @Value("\${rabbitmq.routing.key}")
    private val orderStKey: String,

) {

    private val logger = LoggerFactory.getLogger(MessageService::class.java)

    /**
     * Queue로 메시지를 발행
     *
     * @param messageDto 발행할 메시지의 DTO 객체
     */
    fun sendMessage(messageDto: MessageDto) {
        logger.info("Message sent: {}", messageDto.toString())

        // 각각의 상태에 따라 라우팅 키 지정하여 RabbitMQ로 메시지 발행
        when (messageDto.status) {
            OrderStatus.CONFIRMED -> rabbitTemplate.convertAndSend(exchangeName, orderStKey, messageDto)
            OrderStatus.DE_START -> rabbitTemplate.convertAndSend(exchangeName, orderStKey, messageDto)
            OrderStatus.DE_FINISH -> rabbitTemplate.convertAndSend(exchangeName, orderStKey, messageDto)
            else -> {
                logger.warn("Unhandled order status: ${messageDto.status}")
            }
        }
    }
}
