package com.teamsparta.delivery_system.domain.entity

import jakarta.persistence.*

@Entity
class Store(
    @ManyToOne(fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
    @JoinColumn(name = "member_id", nullable = false)
    val member: Member,

    name: String, address: String,
    content: String, phone: String,
    minDeliveryPrice: Long, deliveryTip: Long,
    minDeliveryTime: Long, maxDeliveryTime: Long
) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val storeId: Long? = null

    @Column(name = "name")
    var name: String = name

    @Column(name = "address")
    var address: String = address

    @Column(name = "content")
    var content: String = content

    @Column(name = "phone")
    var phone: String = phone

    @Column(name = "minDeliveryPrice")
    var minDeliveryPrice = minDeliveryPrice

    @Column(name = "deliveryTip")
    var deliveryTip = deliveryTip

    @Column(name = "minDeliveryTime")
    var minDeliveryTime = minDeliveryTime

    @Column(name = "maxDeliveryTime")
    var maxDeliveryTime = maxDeliveryTime

    @OneToMany(mappedBy = "store", cascade = [CascadeType.ALL], orphanRemoval = true)
    var menus: MutableList<Menu> = ArrayList()

    @OneToMany(mappedBy = "store", cascade = [CascadeType.ALL], orphanRemoval = true)
    var orders: MutableList<Order> = ArrayList()

    @OneToMany(mappedBy = "store", cascade = [CascadeType.ALL], orphanRemoval = true)
    var reviews: MutableList<Review> = ArrayList()

}