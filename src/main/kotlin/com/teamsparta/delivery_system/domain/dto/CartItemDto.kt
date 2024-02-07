package com.teamsparta.delivery_system.domain.dto

import com.teamsparta.delivery_system.domain.entity.CartItem

data class CartItemDto(
    val storeId: Long?,
    val menuId: Long?,
    var menuName: String,
    var menuPrice: Int,
    var quantity: Int
) {

    companion object {
        fun fromEntity(cartItem: CartItem): CartItemDto {
            return CartItemDto(
                storeId = cartItem.menu.store.storeId,
                menuId = cartItem.menu.menuId,
                menuName = cartItem.menu.name,
                menuPrice = cartItem.menu.price,
                quantity = cartItem.quantity
            )
        }
    }
}