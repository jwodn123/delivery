package com.teamsparta.delivery_system.domain.entity

import jakarta.persistence.*

@Entity
class Cart(

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    val member: Member,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id", nullable = false)
    val menu: Menu,

    @Column(name = "quantity")
    var quantity: Int
) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val cartId: Long? = null

    fun addQuantity(quantity: Int) {
        this.quantity += quantity
    }
}