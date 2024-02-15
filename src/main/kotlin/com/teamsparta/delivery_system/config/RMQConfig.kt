package com.teamsparta.delivery_system.config

import org.springframework.amqp.core.Binding
import org.springframework.amqp.core.BindingBuilder
import org.springframework.amqp.core.DirectExchange
import org.springframework.amqp.core.Queue
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

    @Value("\${rabbitmq.queue.name}")
    private val orderStQueue: String,

//    @Value("\${rabbitmq.queue.name.payment}")
//    private val paymentQueueName: String,
//
//    @Value("\${rabbitmq.queue.name.confirmation}")
//    private val confirmationQueueName: String,
//
//    @Value("\${rabbitmq.queue.name.deliveryStart}")
//    private val deliveryStartQueueName: String,

    @Value("\${rabbitmq.exchange.name}")
    private val exchangeName: String,

    @Value("\${rabbitmq.routing.key}")
    private val orderStKey: String,

//    @Value("\${rabbitmq.routing.key.payment}")
//    private val paymentRoutingKey: String,
//
//    @Value("\${rabbitmq.routing.key.confirmation}")
//    private val confirmationRoutingKey: String,
//
//    @Value("\${rabbitmq.routing.key.deliveryStart}")
//    private val deliveryStartRoutingKey: String
) {


    /**
     * Queue
     * 결제 완료 -> 주문 확정
     * 주문 확정 -> 배달 시작
     * 배달 시작 -> 배달 완료
     */
    @Bean
    fun orderStatusQueue(): Queue {
        return Queue(orderStQueue)
    }
//    @Bean
//    fun paymentQueue(): Queue {
//        return Queue(paymentQueueName)
//    }
//
//    @Bean
//    fun confirmationQueue(): Queue {
//        return Queue(confirmationQueueName)
//    }
//
//    @Bean
//    fun deliveryStartQueue(): Queue {
//        return Queue(deliveryStartQueueName)
//    }

    /**
     * 각 Queue에 Binding 설정
     * 1 - paymentBinding
     * 2 - confirmationBinding
     * 3 - deliveryStartBinding
     */
//    @Bean
//    fun paymentBinding(paymentQueue: Queue, exchange: DirectExchange): Binding {
//        return BindingBuilder.bind(paymentQueue).to(exchange).with(paymentRoutingKey)
//    }
//
//    @Bean
//    fun confirmationBinding(confirmationQueue: Queue, exchange: DirectExchange): Binding {
//        return BindingBuilder.bind(confirmationQueue).to(exchange).with(confirmationRoutingKey)
//    }
//
//    @Bean
//    fun deliveryStartBinding(deliveryStartQueue: Queue, exchange: DirectExchange): Binding {
//        return BindingBuilder.bind(deliveryStartQueue).to(exchange).with(deliveryStartRoutingKey)
//    }

    /**
     * Queue에 Binding 설정
     */
    @Bean
    fun orderStatusBinding(orderStQueue: Queue, exchange: DirectExchange): Binding {
        return BindingBuilder.bind(orderStQueue).to(exchange).with(orderStKey)
    }


    /**
     * 지정된 익스체인지 이름으로 DirectExchange 빈을 생성
     *
     * @return TopicExchange 빈 객체
     */
    @Bean
    fun exchange(): DirectExchange {
        return DirectExchange(exchangeName)
    }

    /**
     * RabbitMQ 연결을 위한 ConnectionFactory 빈을 생성하여 반환
     *
     * @return ConnectionFactory 객체
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
