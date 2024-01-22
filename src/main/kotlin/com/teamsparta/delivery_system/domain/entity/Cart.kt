package com.teamsparta.delivery_system.domain.entity

import jakarta.persistence.*

@Entity
class Cart(
    @ManyToOne(fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
    @JoinColumn(name = "member_id", nullable = false)
    val member: Member

) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val cartId: Long? = null

}