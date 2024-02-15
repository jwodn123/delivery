package com.teamsparta.delivery_system.config

import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.launch.support.RunIdIncrementer
import org.springframework.batch.item.ItemProcessor
import org.springframework.batch.item.ItemReader
import org.springframework.batch.item.ItemWriter
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.item.amqp.AmqpItemReader
import com.teamsparta.delivery_system.domain.dto.MessageDto
import com.teamsparta.delivery_system.domain.enums.OrderStatus
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing

@Configuration
@EnableBatchProcessing
class RabbitMQReprocessingJobConfig(
    private val jobBuilderFactory: JobBuilderFactory,
    private val stepBuilderFactory: StepBuilderFactory,
    private val rabbitTemplate: RabbitTemplate,

    @Value("\${rabbitmq.exchange.name}")
    private val exchange: String,

    @Value("\${rabbitmq.routing.key.payment}")
    private val paymentRoutingKey: String,

    @Value("\${rabbitmq.routing.key.confirmation}")
    private val confirmationRoutingKey: String,

    @Value("\${rabbitmq.routing.key.deliveryStart}")
    private val deliveryStartRoutingKey: String

) {

    @Bean
    fun rabbitMQReader(
        rabbitTemplate: RabbitTemplate,
        @Value("\${rabbitmq.queue.name.payment}") paymentQueueName: String,
        @Value("\${rabbitmq.queue.name.confirmation}") confirmationQueueName: String,
        @Value("\${rabbitmq.queue.name.deliveryStart}") deliveryStartQueueName: String
    ): ItemReader<MessageDto> {
        val reader = AmqpItemReader<MessageDto>(rabbitTemplate)
        reader.queue("retry-queue")

//        when (messageDto.status) {
//            OrderStatus.CONFIRMED -> reader.setQueueNames(confirmationQueueName)
//            OrderStatus.DE_START -> reader.setQueueNames(deliveryStartQueueName)
//            OrderStatus.DE_FINISH -> reader.setQueueNames(deliveryFinishQueueName)
//            else -> {
//                throw IllegalArgumentException("Unhandled order status: ${messageDto.status}")
//            }
//        }

        return reader
    }

    @Bean
    fun itemProcessor(): ItemProcessor<MessageDto, String> {
        // 필요에 따라 메시지를 가공하거나 검증하는 ItemProcessor를 구현
        return ItemProcessor { messageDto ->
            // 가공 또는 검증 로직 구현
            messageDto.toString()
        }
    }

    @Bean
    fun logProcessedMessagesWriter(): ItemWriter<String> {
        // 가공된 메시지를 로그에 출력하는 ItemWriter를 구현
        return ItemWriter { messages ->
            messages.forEach { message ->
                println("Processed Message: $message")
            }
        }
    }

    @Bean
    fun processMessagesJob(processMessagesStep: Step): Job {
        return jobBuilderFactory.get("processMessagesJob")
            .incrementer(RunIdIncrementer())
            .start(processMessagesStep)
            .build()
    }

    @Bean
    fun processMessagesStep(
        rabbitMQReader: ItemReader<MessageDto>,
        itemProcessor: ItemProcessor<MessageDto, String>,
        logProcessedMessagesWriter: ItemWriter<String>
    ): Step {
        return stepBuilderFactory.get("processMessagesStep")
            .chunk<MessageDto, String>(10)
            .reader(rabbitMQReader)
            .processor(itemProcessor)
            .writer(logProcessedMessagesWriter)
            .build()
    }

}
