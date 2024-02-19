//package com.teamsparta.delivery_system.integrationtest.service
//
//import com.teamsparta.delivery_system.domain.dto.MessageDto
//import com.teamsparta.delivery_system.domain.entity.Member
//import com.teamsparta.delivery_system.domain.enums.MemberRole
//import com.teamsparta.delivery_system.domain.enums.OrderStatus
//import com.teamsparta.delivery_system.integrationtest.IntegrationTest
//import com.teamsparta.delivery_system.messaging.consumer.OrderStatusEventConsumer
//import com.teamsparta.delivery_system.repository.MemberRepository
//import com.teamsparta.delivery_system.service.CartService
//import com.teamsparta.delivery_system.service.MessageService
//import io.mockk.every
//import io.mockk.mockk
//import org.springframework.beans.factory.annotation.Autowired
//import org.junit.jupiter.api.Assertions.*
//import org.junit.jupiter.api.Test
//import org.springframework.amqp.rabbit.core.RabbitTemplate
//import org.springframework.transaction.annotation.Transactional
//import org.springframework.amqp.core.Queue
//import org.springframework.beans.factory.annotation.Value
//import java.util.*
//
//class OrderStatusServiceTest: IntegrationTest() {
//
//    @Autowired
//    lateinit var messageService: MessageService
//
//    @Autowired
//    lateinit var rabbitTemplate: RabbitTemplate
//
//    @Autowired
//    lateinit var orderStatusEventConsumer: OrderStatusEventConsumer
//
//    var memberRepository: MemberRepository = mockk()
//
//    @Autowired
//    lateinit var errorQueue: Queue
//
//    @Value("\${rabbitmq.exchange.name.orderStatus}")
//    private lateinit var ORDER_EXCHANGE: String
//
//    @Value("\${rabbitmq.queue.name.orderStatus}")
//    private lateinit var ORDER_STATUS_QUEUE: String
//
//    @Value("\${rabbitmq.routing.key.orderStatus}")
//    private lateinit var ORDER_KEY: String
//
//    @Test
//    fun 메시지가_주문_큐에서_consume_되는지_확인 () {
//        // Given
//        val member = Member("ow@naver.com", "12341234", "01023456789", "owner", "서울시 강남구", MemberRole.OWNER)
//        every { memberRepository.findById(any()) } returns Optional.of(member)
//        val messages = mutableListOf<MessageDto>()
//
//        val statusList = listOf(
//            OrderStatus.CONFIRMED,
//            OrderStatus.DE_START,
//            OrderStatus.DE_FINISH,
//        )
//
//        repeat(11) {
//            val randomStatus = statusList.random()
//            val messageDto = MessageDto(
//                memberId = 4L,
//                name = "jjw",
//                phoneNumber = "01037286657",
//                status = randomStatus
//            )
//            messages.add(messageDto)
//        }
//
//        // When
//        messages.forEach { messageService.sendMessage(it) }
//        // RabbitMQ consumer가 메시지를 소비하고 처리하는 시간을 고려하여 대기 시간 설정
////        Thread.sleep(10000)
//
//        // then
//    }
//
//
//}