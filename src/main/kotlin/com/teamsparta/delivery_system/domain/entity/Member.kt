package com.teamsparta.delivery_system.domain.entity

import com.fasterxml.jackson.annotation.JsonManagedReference
import com.teamsparta.delivery_system.domain.enums.MemberRole
import jakarta.persistence.*

@Entity
class Member(
    useremail: String,
    password: String,
    phone: String,
    nickname: String,
    address: String,
    role: MemberRole
) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val memberId: Long? = null

    @Column(name = "userid", unique = true)
    var useremail: String = useremail

    @Column(name = "password")
    var password: String = password

    @Column(name = "phone")
    var phone: String = phone

    @Column(name = "nickname")
    val nickname: String = nickname

    @Column(name = "address")
    var address: String = address

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    var role: MemberRole = role

    @OneToMany(mappedBy = "member", cascade = [CascadeType.ALL], orphanRemoval = true)
    var stores: MutableList<Store> = ArrayList()

    @OneToMany(mappedBy = "member", cascade = [CascadeType.ALL], orphanRemoval = true)
    var favorites: MutableList<Favorite> = ArrayList()

    @OneToMany(mappedBy = "member", cascade = [CascadeType.ALL], orphanRemoval = true)
    var carts: MutableList<Cart> = ArrayList()


}