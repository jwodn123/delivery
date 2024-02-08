package com.teamsparta.delivery_system.controller

import com.teamsparta.delivery_system.web.request.QueueRequest
import lombok.RequiredArgsConstructor
import lombok.extern.slf4j.Slf4j
import org.hibernate.query.sqm.tree.SqmNode.log
import org.springframework.amqp.core.AmqpAdmin
import org.springframework.amqp.core.Binding
import org.springframework.amqp.core.BindingBuilder
import org.springframework.amqp.core.TopicExchange
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.web.bind.annotation.*
import org.springframework.amqp.core.Queue

@Slf4j
@RestController
@RequestMapping("/rabbit")
@RequiredArgsConstructor
class RMQController(
    private val topicExchange: TopicExchange,
    private val rabbitTemplate: RabbitTemplate,
    private val rabbitAdmin: AmqpAdmin

) {

    @PostMapping("/register")
    fun register(@RequestBody queueRequest: QueueRequest): String {
        val queueName: String = queueRequest.queue!!
        val routingKey = "com.devjaewoo.order.*"
        log.info("Binding queue $queueName with Routing key $routingKey")

        val binding: Binding = BindingBuilder.bind(Queue(queueName)).to(topicExchange).with(routingKey)
        rabbitAdmin.declareBinding(binding)

        return "{\"result\": \"Success\"}"
    }

    @GetMapping("/publish/{id}")
    fun publish(@PathVariable id: Long): String {
        val message = "Ordered ID: $id"
        val routingKey = "com.devjaewoo.order.$id"

        log.info("Publish message $message to $routingKey")
        rabbitTemplate.convertAndSend("com.exchange", routingKey, message)

        return "Publish Success"
    }
}
