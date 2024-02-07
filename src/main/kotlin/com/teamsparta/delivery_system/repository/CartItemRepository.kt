package com.teamsparta.delivery_system.repository

import com.teamsparta.delivery_system.domain.entity.CartItem
import org.springframework.data.jpa.repository.JpaRepository

interface CartItemRepository: JpaRepository<CartItem, Long> {
}