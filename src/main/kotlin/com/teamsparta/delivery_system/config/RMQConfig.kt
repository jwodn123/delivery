package com.teamsparta.delivery_system.config

import org.springframework.amqp.core.AmqpAdmin
import org.springframework.amqp.core.TopicExchange
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory
import org.springframework.amqp.rabbit.connection.ConnectionFactory
import org.springframework.amqp.rabbit.core.RabbitAdmin
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
class RMQConfig {
    @Value("\${spring.rabbitmq.host:localhost}")
    private val host: String? = null

    @Value("\${spring.rabbitmq.port:5672}")
    private val port = 0

    @Value("\${spring.rabbitmq.username}")
    private val username: String? = null

    @Value("\${spring.rabbitmq.password}")
    private val password: String? = null

    @Bean
    // Topic 형식의 Exchange를 생성하는데 사용.
    // Topic Exchange는 메시지 라우팅에 사용되며, 라우팅 키를 사용하여 메시지를 다른 큐로 전송.
    fun topicExchange(): TopicExchange {
        val EXCHANGE_NAME = "com.exchange"
        return TopicExchange(EXCHANGE_NAME)
    }

    @Bean
    // RabbitMQ와의 연결을 설정하는 ConnectionFactory를 생성
    // CachingConnectionFactory를 사용하여 RabbitMQ와의 TCP 연결을 관리
    fun connectionFactory(): ConnectionFactory {
        val connectionFactory = CachingConnectionFactory(host, port)
        connectionFactory.username = username!!
        connectionFactory.setPassword(password!!)

        return connectionFactory
    }

    @Bean
    // RabbitMQ 관리 도구를 설정
    // RabbitAdmin은 RabbitMQ에서 Exchange, Queue 등을 관리하기 위한 도구
    // 여기서 RabbitAdmin 사용하여 topicExchange를 선언하고 반환.
    fun amqpAdmin(): AmqpAdmin {
        val rabbitAdmin: RabbitAdmin = RabbitAdmin(connectionFactory())
        rabbitAdmin.declareExchange(topicExchange())
        return rabbitAdmin
    }
}