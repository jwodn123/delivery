//package com.teamsparta.delivery_system.config
//
//import org.springframework.batch.core.Job
//import org.springframework.batch.core.Step
//import org.springframework.batch.core.launch.support.RunIdIncrementer
//import org.springframework.batch.item.ItemProcessor
//import org.springframework.batch.item.ItemReader
//import org.springframework.batch.item.ItemWriter
//import org.springframework.beans.factory.annotation.Value
//import org.springframework.context.annotation.Bean
//import org.springframework.context.annotation.Configuration
//import org.springframework.amqp.rabbit.core.RabbitTemplate
//import org.springframework.batch.item.amqp.AmqpItemReader
//import com.teamsparta.delivery_system.domain.dto.MessageDto
//import org.springframework.amqp.core.Message
//import org.springframework.amqp.rabbit.annotation.RabbitListener
//import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing
//import org.springframework.batch.core.job.builder.JobBuilder
//import org.springframework.batch.core.repository.JobRepository
//import org.springframework.batch.core.step.builder.StepBuilder
//import org.springframework.transaction.PlatformTransactionManager
//
//@Configuration
//class RabbitMQReprocessingJobConfig(
//    private val jobRepository: JobRepository,
//    private val transactionManager: PlatformTransactionManager,
//    private val tasklet: SampleTasklet,
//    private val rabbitTemplate: RabbitTemplate,
//
//    /**
//     * Exchange
//     */
//    @Value("\${rabbitmq.exchange.name.orderStatus}")
//    private val ORDER_EXCHANGE: String,
//
//    @Value("\${rabbitmq.exchange.name.retry}")
//    private val RETRY_EXCHANGE: String,
//
//    @Value("\${rabbitmq.exchange.name.error}")
//    private val ERROR_EXCHANGE: String,
//
//    /**
//     * Queue
//     */
//    @Value("\${rabbitmq.queue.name.orderStatus}")
//    private val ORDER_STATUS_QUEUE: String,
//
//    @Value("\${rabbitmq.queue.name.retry}")
//    private val RETRY_QUEUE: String,
//
//    @Value("\${rabbitmq.queue.name.error}")
//    private val ERROR_QUEUE: String,
//
//    /**
//     * Routing Key
//     */
//    @Value("\${rabbitmq.routing.key.orderStatus}")
//    private val ORDER_KEY: String,
//
//    @Value("\${rabbitmq.routing.key.retry}")
//    private val RETRY_KEY: String,
//
//    @Value("\${rabbitmq.routing.key.error}")
//    private val ERROR_KEY: String,
//
//) {
//
//    @Bean
//    fun rabbitMQReader(
//        rabbitTemplate: RabbitTemplate,
//    ): AmqpItemReader<Message> {
//
//        // RabbitTemplate이 사용할 기본 수신 큐 설정
//        rabbitTemplate.setDefaultReceiveQueue("orderStQueue")
//
//        val reader = AmqpItemReader<Message>(rabbitTemplate)
//
//        // 스프링 배치의 ItemReader에서 read 메소드를 호출하면서 메시지를 읽어옴
//        val msg = reader.read()
//
//        // 메시지가 존재하는 경우에만 처리
//        if (msg != null) {
//            // x-death 속성을 가져와서 재시도 횟수 확인
//            val xDeathHeader = msg.messageProperties.xDeathHeader
//            if (xDeathHeader != null) {
//                val retryCount = xDeathHeader[0]["count"] as? Long ?: 0
//
//                // 1. message의 xDeath 를 검사해서 Retry Count 보다 적으면, Retry Exchange retry Queue publish
//                if (retryCount < 3) {
//                    rabbitTemplate.send(RETRY_EXCHANGE, RETRY_QUEUE, msg)
//                }
//                // 2. message의 xDeath 를 검사해서 Retry Count 보다 많으면, Error Exchange error Queue publish
//                else {
//                    rabbitTemplate.send(ERROR_EXCHANGE, ERROR_QUEUE, msg)
//                }
//            }
//        }
//
//        return reader
//    }
//
//    @Bean
//    fun itemProcessor(): ItemProcessor<MessageDto, String> {
//        // 필요에 따라 메시지를 가공하거나 검증하는 ItemProcessor를 구현
//        return ItemProcessor { messageDto ->
//            // 가공 또는 검증 로직 구현
//            messageDto.toString()
//        }
//    }
//
//    @Bean
//    fun logProcessedMessagesWriter(): ItemWriter<String> {
//        // 가공된 메시지를 로그에 출력하는 ItemWriter를 구현
//        return ItemWriter { messages ->
//            messages.forEach { message ->
//                println("Processed Message: $message")
//            }
//        }
//    }
//
//    @Bean
//    fun processMessagesJob(processMessagesStep: Step): Job {
//        return JobBuilder("job", jobRepository)
//            .incrementer(RunIdIncrementer())
//            .start(processMessagesStep)
//            .build()
//    }
//
//    @Bean
//    fun processMessagesStep(
//        rabbitMQReader: ItemReader<Message>,
//        itemProcessor: ItemProcessor<MessageDto, String>,
//        logProcessedMessagesWriter: ItemWriter<String>
//    ): Step {
//        return StepBuilder("step", jobRepository)
//            .tasklet(tasklet, transactionManager)
//            .build()
//    }
//
//}
