package com.teamsparta.delivery_system.domain.dto

import com.teamsparta.delivery_system.domain.entity.Cart
import com.teamsparta.delivery_system.domain.entity.CartItem
import com.teamsparta.delivery_system.domain.entity.Menu

data class CartDto(
    val cartId: Long?,
    val cartItems: List<CartItemDto>

) {

    companion object {
        fun fromEntity(cart: Cart): CartDto {
            val dto = CartDto(
                cartId = cart.cartId,
                cartItems = cart.cartItems.map {
                    CartItemDto.fromEntity(it)
                }
            )
            return dto
        }

    }
}