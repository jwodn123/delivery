package com.teamsparta.delivery_system.domain.entity

import jakarta.persistence.*

@Entity
class Favorite(
    @ManyToOne(fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
    @JoinColumn(name = "member_id", nullable = false)
    val member: Member,

    @ManyToOne(fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
    @JoinColumn(name = "menu_id", nullable = false)
    val menu: Menu
) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val favoriteId: Long? = null

}