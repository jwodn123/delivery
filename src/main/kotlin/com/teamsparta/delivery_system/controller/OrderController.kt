package com.teamsparta.delivery_system.controller

import com.teamsparta.delivery_system.domain.dto.MessageDto
import com.teamsparta.delivery_system.domain.dto.OrderDto
import com.teamsparta.delivery_system.domain.dto.PayApproveResDto
import com.teamsparta.delivery_system.domain.dto.StoreDto
import com.teamsparta.delivery_system.exception.BadRequestException
import com.teamsparta.delivery_system.service.CartService
import com.teamsparta.delivery_system.service.OrderService
import com.teamsparta.delivery_system.web.request.OrderRequest
import com.teamsparta.delivery_system.web.request.PayCompleteRequest
import com.teamsparta.delivery_system.web.response.ListResponse
import com.teamsparta.delivery_system.web.response.SingleResponse
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.User
import org.springframework.web.bind.annotation.*

@RequestMapping("/api/orders")
@RestController
class OrderController(
    private val orderService: OrderService,
) {

    /**
     * 주문하기
     */
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

    /**
     * 사용자 주문 리스트
     */
    @GetMapping
    fun customerOrderLists(
        @AuthenticationPrincipal user: User
    ): ResponseEntity<ListResponse<OrderDto>> {
        val orders = orderService.getCustomerOrders(user.username.toLong())
        return ResponseEntity(ListResponse.successOf(orders), HttpStatus.OK)
    }


    /**
    * 사용자 주문 취소하기
    */
    @DeleteMapping("/{orderId}")
    fun cacelOrder(
        @AuthenticationPrincipal user: User,
        @PathVariable orderId: Long
    ): ResponseEntity<SingleResponse<String>> {
        orderService.cancelOrder(user.username.toLong(), orderId)
        return ResponseEntity(SingleResponse.success(), HttpStatus.OK)
    }

    /**
     * 결제 성공 후
     * 결제 대기 -> 결제 완료
     */
    @PostMapping("/{orderId}/complete")
    fun afterPayRequest(
        @AuthenticationPrincipal user: User,
        @PathVariable orderId: Long
    ): ResponseEntity<SingleResponse<Long>> {
        val storeId = orderService.completeOrder(user.username.toLong(), orderId)
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(SingleResponse.successOf("가게에서 주문 확인 중입니다!", storeId))
    }

    /**
     * 매장 사장님 주문 상태 변경(배달 시작, 배달 완료 같은 경우 원래는 라이더가 직접 변경하는 것이지만 이 프로젝트에서는 매장 사장님이 하는 것으로 변경)
     * 1 - 결제 완료 -> 주문 확정
     * 2 - 주문 확정 -> 배달 시작
     * 3 - 배달 시작 -> 배달 완료
     */
    @PostMapping("/{orderId}/stores/{storeId}/confirm")
    //@PreAuthorize("hasRole('OWNER')")
    fun confirmOrder(
        @AuthenticationPrincipal user: User,
        @PathVariable orderId: Long,
        @PathVariable storeId: Long
    ): ResponseEntity<SingleResponse<String>> {
        orderService.confirmOrder(user.username.toLong(), storeId, orderId)
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(SingleResponse.successOf("주문 상태가 변경되었습니다!"))
    }

    /**
     * 매장 사장님 주문내역
     */
    @GetMapping("/stores/{storeId}")
    fun ownerOrderLists(
        @AuthenticationPrincipal user: User,
        @PathVariable storeId: Long
    ): ResponseEntity<ListResponse<OrderDto>> {
        val orders = orderService.ownerOrderLists(user.username.toLong(), storeId)
        return ResponseEntity(ListResponse.successOf(orders), HttpStatus.OK)
    }

}