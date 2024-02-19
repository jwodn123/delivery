package com.teamsparta.delivery_system.config

import org.springframework.amqp.core.Binding
import org.springframework.amqp.core.BindingBuilder
import org.springframework.amqp.core.DirectExchange
import org.springframework.amqp.core.Queue
import org.springframework.amqp.core.QueueBuilder
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory
import org.springframework.amqp.rabbit.connection.ConnectionFactory
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter
import org.springframework.amqp.support.converter.MessageConverter
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment

@Configuration
class RMQConfig(
    private val env: Environment,

    @Value("\${spring.rabbitmq.host}")
    private val host: String,

    @Value("\${spring.rabbitmq.port}")
    private val port: Int,

    @Value("\${spring.rabbitmq.username}")
    private val username: String,

    @Value("\${spring.rabbitmq.password}")
    private val password: String,

    /**
     * Exchange
     */
    @Value("\${rabbitmq.exchange.name.orderStatus}")
    private val ORDER_EXCHANGE: String,

    @Value("\${rabbitmq.exchange.name.error}")
    private val ERROR_EXCHANGE: String,

    /**
     * Queue
     */
    @Value("\${rabbitmq.queue.name.orderStatus}")
    private val ORDER_STATUS_QUEUE: String,

    @Value("\${rabbitmq.queue.name.error}")
    private val ERROR_QUEUE: String,

    /**
     * Routing Key
     */
    @Value("\${rabbitmq.routing.key.orderStatus}")
    private val ORDER_KEY: String,

    @Value("\${rabbitmq.routing.key.error}")
    private val ERROR_KEY: String,

) {


    /**
     * Queue
     * 결제 완료 -> 주문 확정
     * 주문 확정 -> 배달 시작
     * 배달 시작 -> 배달 완료
     */
    @Bean
    fun orderStatusQueue(): Queue {
        return QueueBuilder.durable(ORDER_STATUS_QUEUE)
            .withArgument("x-dead-letter-exchange", ERROR_EXCHANGE)
            .withArgument("x-dead-letter-routing-key", ERROR_KEY)
            .build()
    }

    @Bean
    fun errorQueue(): Queue {
        return QueueBuilder.durable(ERROR_QUEUE).build()
    }

    /**
     * Queue에 Binding 설정
     */
    @Bean
    fun orderStatusBinding(orderStatusQueue: Queue, orderExchange: DirectExchange): Binding {
        return BindingBuilder.bind(orderStatusQueue).to(orderExchange).with(ORDER_KEY)
    }

    @Bean
    fun errorBinding(errorQueue: Queue, errorExchange: DirectExchange): Binding {
        return BindingBuilder.bind(errorQueue).to(errorExchange).with(ERROR_KEY)
    }


    /**
     * 지정된 익스체인지 이름으로 DirectExchange 빈을 생성
     */
    @Bean
    fun orderExchange(): DirectExchange {
        return DirectExchange(ORDER_EXCHANGE)
    }

    @Bean
    fun errorExchange(): DirectExchange {
        return DirectExchange(ERROR_EXCHANGE)
    }

    /**
     * RabbitMQ 연결을 위한 ConnectionFactory 빈을 생성하여 반환
     */
    @Bean
    fun connectionFactory(): ConnectionFactory {
        val connectionFactory = CachingConnectionFactory()
        connectionFactory.setHost(host);
        connectionFactory.setPort(port);
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password)
        return connectionFactory
    }

    /**
     * RabbitTemplate을 생성하여 반환
     *
     * @param connectionFactory RabbitMQ와의 연결을 위한 ConnectionFactory 객체
     * @return RabbitTemplate 객체
     */
    @Bean
    fun rabbitTemplate(connectionFactory: ConnectionFactory): RabbitTemplate {
        val rabbitTemplate = RabbitTemplate(connectionFactory)
        // JSON 형식의 메시지를 직렬화하고 역직렬할 수 있도록 설정
        rabbitTemplate.messageConverter = jackson2JsonMessageConverter()
        return rabbitTemplate
    }

    /**
     * Jackson 라이브러리를 사용하여 메시지를 JSON 형식으로 변환하는 MessageConverter 빈을 생성
     *
     * @return MessageConverter 객체
     */
    @Bean
    fun jackson2JsonMessageConverter(): MessageConverter {
        return Jackson2JsonMessageConverter()
    }
}
