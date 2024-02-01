package com.teamsparta.delivery_system.controller

import com.teamsparta.delivery_system.domain.dto.OrderDto
import com.teamsparta.delivery_system.domain.dto.StoreDto
import com.teamsparta.delivery_system.exception.BadRequestException
import com.teamsparta.delivery_system.service.OrderService
import com.teamsparta.delivery_system.web.request.OrderRequest
import com.teamsparta.delivery_system.web.response.ListResponse
import com.teamsparta.delivery_system.web.response.SingleResponse
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.User
import org.springframework.web.bind.annotation.*

@RequestMapping("/api/orders")
@RestController
class OrderController(
    private val orderService: OrderService
) {

    // 주문하기
    @PostMapping
    fun placeOrder(
        @AuthenticationPrincipal user: User,
        @Valid @RequestBody request: OrderRequest
    ): ResponseEntity<SingleResponse<OrderDto>> {
        try {
            val order = orderService.placeOrder(user.username.toLong(), request)
            return ResponseEntity
                .status(HttpStatus.OK)
                .body(SingleResponse.successOf("메뉴 주문에 성공했습니다!", OrderDto.fromEntity(order)))
        } catch (e: BadRequestException) {
            throw BadRequestException("주문 실패!!")
        }

    }

    // 주문 리스트
    @GetMapping
    fun orderLists(
        @AuthenticationPrincipal user: User
    ): ResponseEntity<ListResponse<OrderDto>> {
        val orders = orderService.getAllOrders(user.username.toLong())
        return ResponseEntity(ListResponse.successOf(orders), HttpStatus.OK)
    }

    // 사용자 주문 취소하기
    @DeleteMapping("/{orderId}")
    fun cacelOrder(
        @AuthenticationPrincipal user: User,
        @PathVariable orderId: Long
    ): ResponseEntity<SingleResponse<String>> {
        orderService.cancelOrder(user.username.toLong(), orderId)
        return ResponseEntity(SingleResponse.success(), HttpStatus.OK)
    }


}