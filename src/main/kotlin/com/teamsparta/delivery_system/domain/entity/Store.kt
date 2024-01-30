package com.teamsparta.delivery_system.domain.entity

import com.teamsparta.delivery_system.domain.entity.common.BaseTimeEntity
import jakarta.persistence.*

@Entity
class Store(
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    val member: Member,

    @Column(name = "name")
    var name: String,

    @Column(name = "address")
    var address: String,

    @Column(name = "content")
    var content: String,

    @Column(name = "phone")
    var phone: String,

    @Column(name = "minDeliveryPrice")
    var minDeliveryPrice: Int,

    @Column(name = "deliveryTip")
    var deliveryTip: Int,

    @Column(name = "minDeliveryTime")
    var minDeliveryTime: Int,

    @Column(name = "maxDeliveryTime")
    var maxDeliveryTime: Int,

) {

    init {
        member.stores.add(this)
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val storeId: Long? = null

    @OneToMany(mappedBy = "store", cascade = [CascadeType.ALL])
    @OrderBy("store_id DESC")
    var menus: MutableList<Menu> = mutableListOf()

    fun update(name: String, address: String, content: String, phone: String, minDeliveryPrice: Int, deliveryTip: Int, minDeliveryTime: Int, maxDeliveryTime: Int) {
        this.name = name
        this.address = address
        this.content = content
        this.phone = phone
        this.minDeliveryPrice = minDeliveryPrice
        this.deliveryTip = deliveryTip
        this.minDeliveryTime = minDeliveryTime
        this.maxDeliveryTime = maxDeliveryTime
    }
}