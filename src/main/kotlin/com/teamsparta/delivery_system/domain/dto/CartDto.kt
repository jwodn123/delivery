package com.teamsparta.delivery_system.domain.dto

import com.teamsparta.delivery_system.domain.entity.Cart
import com.teamsparta.delivery_system.domain.entity.Menu

data class CartDto(

    //val cartId: Long?,
    val storeId: Long?,
    val menuId: Long?,
    var menuName: String,
    var menuPrice: Long,
    var quantity: Int

) {

    companion object {
        fun fromEntity(cart: Cart): CartDto {
            val dto = CartDto(
                //cartId = cart.cartId,
                storeId = cart.menu.store.storeId,
                menuId = cart.menu.menuId,
                menuName = cart.menu.name,
                menuPrice = cart.menu.price,
                quantity = cart.quantity
            )
            return dto
        }

        fun fromEntities(carts: List<Cart>): List<CartDto> {
            return carts.map {
                val dto = CartDto(
                    //cartId = it.cartId,
                    storeId = it.menu.store.storeId,
                    menuId = it.menu.menuId,
                    menuName = it.menu.name,
                    menuPrice = it.menu.price,
                    quantity = it.quantity
                )

                dto
            }
        }
    }
}