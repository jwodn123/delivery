package com.teamsparta.delivery_system.domain.entity

import com.teamsparta.delivery_system.domain.entity.common.BaseTimeEntity
import com.teamsparta.delivery_system.domain.enums.Category
import jakarta.persistence.*

@Entity
class Menu(
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", nullable = false)
    var store: Store,

    @Column(name = "category")
    @Enumerated(EnumType.STRING)
    var category: Category,

    @Column(name = "name")
    var name: String,

    @Column(name = "explanation")
    var explanation: String,

    @Column(name = "price")
    var price: Int
) {

    init {
        store.menus.add(this)
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val menuId: Long? = null

    fun update(category: Category, name: String, explanation: String, price: Int) {
        this.category = category
        this.name = name
        this.explanation = explanation
        this.price = price
    }
}