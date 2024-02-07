package com.teamsparta.delivery_system.domain.entity

import jakarta.persistence.*

@Entity
class CartItem(

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id", nullable = false)
    val cart: Cart,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id", nullable = false)
    val menu: Menu,

    @Column(name = "quantity")
    var quantity: Int

) {

    init {
        cart.cartItems.add(this)
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val cartItemId: Long? = null

//    fun addQuantity(quantity: Int) {
//        this.quantity += quantity
//    }
}