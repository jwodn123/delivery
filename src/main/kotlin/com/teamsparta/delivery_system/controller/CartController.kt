package com.teamsparta.delivery_system.controller

import com.teamsparta.delivery_system.domain.dto.CartDto
import com.teamsparta.delivery_system.domain.dto.MenuDto
import com.teamsparta.delivery_system.exception.BadRequestException
import com.teamsparta.delivery_system.repository.CartRepository
import com.teamsparta.delivery_system.service.CartService
import com.teamsparta.delivery_system.web.request.CartAddRequest
import com.teamsparta.delivery_system.web.request.MenuCreateRequest
import com.teamsparta.delivery_system.web.response.ListResponse
import com.teamsparta.delivery_system.web.response.SingleResponse
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.User
import org.springframework.web.bind.annotation.*

@RequestMapping("/api/carts")
@RestController
class CartController(
    private val cartService: CartService
) {

    // 장바구니에 메뉴 담기
    @PostMapping
    fun addCart(
        @AuthenticationPrincipal user: User,
        @Valid @RequestBody request: CartAddRequest
    ): ResponseEntity<SingleResponse<CartDto>> {
        try {
            val carts = cartService.addCart(user.username.toLong(), request)
            return ResponseEntity
                .status(HttpStatus.OK)
                .body(SingleResponse.successOf("장바구니 추가에 성공했습니다!", CartDto.fromEntity(carts)))
        } catch (e: BadRequestException) {
            throw BadRequestException("장바구니에는 같은 가게 메뉴만 담을 수 있습니다.")
        }

    }

    // 장바구니 리스트
    @GetMapping
    fun getCartList(): ResponseEntity<ListResponse<CartDto>> {
        val carts = cartService.getCartList()
        return ResponseEntity(ListResponse.successOf(carts), HttpStatus.OK)
    }

    // 장바구니 특정 메뉴 삭제
    @DeleteMapping("/{cartId}/{menuId}")
    fun deleteCartMenu(
        @AuthenticationPrincipal user: User,
        @PathVariable cartId: Long,
        @PathVariable menuId: Long,
    ): ResponseEntity<SingleResponse<String>> {
        cartService.deleteCartMenu(user.username.toLong(), cartId, menuId)
        return ResponseEntity(SingleResponse.success(), HttpStatus.OK)
    }




}