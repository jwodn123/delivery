package com.teamsparta.delivery_system.service

import com.teamsparta.delivery_system.domain.dto.MessageDto
import com.teamsparta.delivery_system.domain.enums.OrderStatus
import com.teamsparta.delivery_system.exception.BadRequestException
import org.slf4j.LoggerFactory
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class MessageService(
    private val rabbitTemplate: RabbitTemplate,

    @Value("\${rabbitmq.exchange.name.orderStatus}")
    private val ORDER_EXCHANGE: String,

    @Value("\${rabbitmq.routing.key.orderStatus}")
    private val ORDER_KEY: String,

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
        if (!messageDto.status.isSendable()) {
            throw Exception("Unhandled order status: ${messageDto.status}")
        }

        rabbitTemplate.convertAndSend(ORDER_EXCHANGE, ORDER_KEY, messageDto)
    }

}
