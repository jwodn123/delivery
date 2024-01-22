package com.teamsparta.delivery_system.domain.entity

import com.teamsparta.delivery_system.domain.enums.MemberRole
import jakarta.persistence.*

@Entity
class Member(userid: String, password: String, role: MemberRole) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val memberId: Long? = null

    @Column(name = "userid", unique = true)
    var userid: String = userid

    @Column(name = "password")
    var password: String = password

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    var role: MemberRole = role

    @OneToMany(mappedBy = "member", cascade = [CascadeType.ALL], orphanRemoval = true)
    var carts: MutableList<Cart> = ArrayList()

}