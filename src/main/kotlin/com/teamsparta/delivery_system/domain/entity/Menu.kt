package com.teamsparta.delivery_system.domain.entity

import jakarta.persistence.*

@Entity
class Menu(
    @ManyToOne(fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
    @JoinColumn(name = "store_id", nullable = false)
    val store: Store,

    category: String,
    name: String,
    explanation: String,
    price: Long
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val menuId: Long? = null

    @Column(name = "category")
    var category: String = category

    @Column(name = "name")
    var name: String = name

    @Column(name = "explanation")
    var explanation: String = explanation

    @Column(name = "price")
    var price = price

    @OneToMany(mappedBy = "menu", cascade = [CascadeType.ALL], orphanRemoval = true)
    var reviews: MutableList<Review> = ArrayList()

    @OneToMany(mappedBy = "menu", cascade = [CascadeType.ALL], orphanRemoval = true)
    var carts: MutableList<Cart> = ArrayList()
}