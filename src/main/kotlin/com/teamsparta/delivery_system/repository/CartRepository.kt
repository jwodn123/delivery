package com.teamsparta.delivery_system.repository

import com.teamsparta.delivery_system.domain.entity.Cart
import com.teamsparta.delivery_system.domain.entity.Member
import com.teamsparta.delivery_system.domain.entity.Menu
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface CartRepository: JpaRepository<Cart, Long> {
    fun findByCartIdAndMember(cartId: Long, member: Member): Optional<Cart>
    fun findByMember(member: Member): Cart?

}