package com.teamsparta.delivery_system.repository

import com.teamsparta.delivery_system.domain.entity.Member
import com.teamsparta.delivery_system.domain.entity.Order
import com.teamsparta.delivery_system.domain.entity.Store
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.time.LocalDate
import java.time.LocalDateTime

@Repository
interface OrderRepository: JpaRepository<Order, Long> {
    fun findByMemberAndCreatedAtAfter(member: Member, createdAt: LocalDateTime): List<Order>
    fun findByStore(store: Store): List<Order>
}