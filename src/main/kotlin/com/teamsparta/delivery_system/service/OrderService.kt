package com.teamsparta.delivery_system.service

import com.teamsparta.delivery_system.domain.dto.MessageDto
import com.teamsparta.delivery_system.domain.dto.OrderDto
import com.teamsparta.delivery_system.domain.entity.Order
import com.teamsparta.delivery_system.domain.enums.MemberRole
import com.teamsparta.delivery_system.domain.enums.OrderStatus
import com.teamsparta.delivery_system.exception.BadRequestException
import com.teamsparta.delivery_system.exception.NotFoundException
import com.teamsparta.delivery_system.repository.CartRepository
import com.teamsparta.delivery_system.repository.MemberRepository
import com.teamsparta.delivery_system.repository.OrderRepository
import com.teamsparta.delivery_system.repository.StoreRepository
import com.teamsparta.delivery_system.web.request.OrderRequest
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class OrderService(
    private val orderRepository: OrderRepository,
    private val cartRepository: CartRepository,
    private val memberRepository: MemberRepository,
    private val storeRepository: StoreRepository,
    private val messageService: MessageService
) {

    /**
     * 주문하기
     */
    fun placeOrder(memberId: Long, request: OrderRequest): Order {
        val member = memberRepository.findById(memberId).orElseThrow { NotFoundException("사용자를 찾을 수 없습니다.") }
        val cart = cartRepository.findById(request.cartId).orElseThrow { NotFoundException("장바구니가 비어 있습니다") }

        // 장바구니에 있는 모든 메뉴의 가격과 수량을 곱하여 총 가격 계산
        val totalPrice = cart.cartItems.sumOf { it.menu.price * it.quantity}

        val order = Order(
            member = member,
            store = cart.store,
            paymentMethod = request.paymentMethod,
            totalPrice = totalPrice,
            requests = request.requests,
            status = OrderStatus.WAITING_PAY,
        )
        return orderRepository.save(order)
    }

    /**
     * 사용자 주문 취소하기
     */
    fun cancelOrder(memberId: Long, orderId: Long) {
        val order = orderRepository.findById(orderId).orElseThrow { NotFoundException("주문 정보가 없습니다.") }

        if(order.status != OrderStatus.CONFIRMED) {
            order.status = OrderStatus.CANCEL
            orderRepository.save(order)
        } else {
            return throw BadRequestException("지금은 주문을 취소할 수 없습니다.")
        }
    }


    /**
     * 사용자 주문 리스트
     */
    fun getCustomerOrders(memberId: Long): List<OrderDto> {
        val member = memberRepository.findById(memberId).orElseThrow { NotFoundException("회원 정보를 찾을 수 없습니다.") }

        // 3개월 내 주문 내역 조회
        val currentDate = LocalDateTime.now()
        val threeMonthsAgo = currentDate.minusMonths(3)

        val orders = orderRepository.findByMemberAndCreatedAtAfter(member, threeMonthsAgo)

        return OrderDto.customerOrderList(orders)
    }

    /**
     * 결제 완료 후 주문내역 전달
     */
    @Transactional
    fun completeOrder(memberId: Long, orderId: Long): Long {
        val order = orderRepository.findById(orderId).orElseThrow { NotFoundException("주문 정보가 없습니다.") }

        // 주문 상태를 COMPLETE_PAY(결제완료)로 변경
        order.status = OrderStatus.COMPLETE_PAY

        return order.store.storeId!!
    }

    /**
     * 매장 사장님 주문 상태 변경
     */
    @Transactional
    fun confirmOrder(memberId: Long, storeId: Long, orderId: Long) {
        val store = storeRepository.findById(storeId).orElseThrow { NotFoundException("주문 정보가 없습니다.") }
        val owner = memberRepository.findById(memberId).orElseThrow { NotFoundException("주문 정보가 없습니다.") }
        if (store.member != owner) {
            throw NotFoundException("매장 사장님 정보와 일치하지 않습니다.")
        }
        val order = orderRepository.findById(orderId).orElseThrow { NotFoundException("주문 정보가 없습니다.") }
        order.confirm(storeId)

        // 주문 상태 변경에 대한 메시지를 RabbitMQ로 발행
        val messageDto = MessageDto(
            memberId = order.member.memberId!!,
            name = order.member.nickname,
            phoneNumber = order.member.phone,
            status = order.status
        )
        messageService.sendMessage(messageDto)
    }

    /**
     * 매장 사장님 주문내역
     */
    fun ownerOrderLists(memberId: Long, storeId: Long): List<OrderDto> {
        val member = memberRepository.findById(memberId).orElseThrow { NotFoundException("회원 정보를 찾을 수 없습니다.") }
        val store = storeRepository.findById(storeId).orElseThrow { NotFoundException("가게 정보를 찾을 수 없습니다.") }
        val orders = orderRepository.findByStore(store)

        return OrderDto.ownerOrderList(orders)
    }

}