package com.teamsparta.delivery_system.domain.entity

import com.teamsparta.delivery_system.domain.entity.common.BaseTimeEntity
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
class Review(
    @ManyToOne(fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
    @JoinColumn(name = "member_id", nullable = false)
    val member: Member,

    @ManyToOne(fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
    @JoinColumn(name = "store_id", nullable = false)
    val store: Store,

    @ManyToOne(fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
    @JoinColumn(name = "menu_id", nullable = false)
    val menu: Menu,

    content: String

): BaseTimeEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val reviewId: Long? = null

    @Column(name = "content")
    var content: String = content
}