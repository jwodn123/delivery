package com.teamsparta.delivery_system.domain.entity

import jakarta.persistence.*

@Entity
class Cart(

    @ManyToOne(fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
    @JoinColumn(name = "menu_id", nullable = false)
    val menu: Menu,

    @ManyToOne(fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
    @JoinColumn(name = "order_id", nullable = false)
    val order: Order,

    @Column(name = "quantity")
    var quantity: Long
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val cartId: Long? = null
}