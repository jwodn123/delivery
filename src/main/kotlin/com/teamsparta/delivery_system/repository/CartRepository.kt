package com.teamsparta.delivery_system.repository

import com.teamsparta.delivery_system.domain.entity.Cart
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CartRepository: JpaRepository<Cart, Long> {
}