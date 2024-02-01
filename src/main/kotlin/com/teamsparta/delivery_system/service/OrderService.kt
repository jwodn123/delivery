package com.teamsparta.delivery_system.service

import com.teamsparta.delivery_system.domain.dto.OrderDto
import com.teamsparta.delivery_system.domain.entity.Member
import com.teamsparta.delivery_system.domain.entity.Order
import com.teamsparta.delivery_system.domain.entity.Store
import com.teamsparta.delivery_system.domain.enums.OrderStatus
import com.teamsparta.delivery_system.exception.BadRequestException
import com.teamsparta.delivery_system.exception.NotFoundException
import com.teamsparta.delivery_system.repository.CartRepository
import com.teamsparta.delivery_system.repository.MemberRepository
import com.teamsparta.delivery_system.repository.OrderRepository
import com.teamsparta.delivery_system.repository.StoreRepository
import com.teamsparta.delivery_system.web.request.OrderRequest
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

@Service
class OrderService(
    private val orderRepository: OrderRepository,
    private val cartRepository: CartRepository,
    private val memberRepository: MemberRepository,
    private val storeRepository: StoreRepository
) {

    // 주문하기
    fun placeOrder(memberId: Long, request: OrderRequest): Order {
        val member = memberRepository.findById(memberId).orElseThrow { NotFoundException("사용자를 찾을 수 없습니다.") }
        val cart = cartRepository.findById(request.cartId).orElseThrow { NotFoundException("장바구니가 비어 있습니다") }

        val order = Order(
            member = member,
            paymentMethod = request.paymentMethod,
            totalPrice = request.totalPrice,
            requests = request.requests,
            status = OrderStatus.WAITING_PAY,
            menu = cart.menu
        )
        return orderRepository.save(order)
    }

    // 주문 리스트
    fun getAllOrders(memberId: Long): List<OrderDto> {
        val member = memberRepository.findById(memberId).orElseThrow { NotFoundException("사용자를 찾을 수 없습니다.") }

        // 3개월 내 주문 내역 조회
        val currentDate = LocalDateTime.now()
        val threeMonthsAgo = currentDate.minusMonths(3)

        val orders = orderRepository.findByMemberAndOrderDateAfter(member, threeMonthsAgo)

        if(member.role.equals("CUSTOMER")) {
            return OrderDto.customerOrderList(orders)
        } else {
            return OrderDto.ownerOrderList(orders)
        }
    }


    // 사용자 주문 취소하기
    fun cancelOrder(memberId: Long, orderId: Long) {
        val order = orderRepository.findById(orderId).orElseThrow { NotFoundException("주문 정보가 없습니다.") }

        if(order.status != OrderStatus.CONFIRMED) {
            order.status = OrderStatus.CANCEL
            orderRepository.save(order)
        } else {
            return throw BadRequestException("지금은 주문을 취소할 수 없습니다.")
        }

    }


}