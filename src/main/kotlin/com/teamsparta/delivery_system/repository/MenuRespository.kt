package com.teamsparta.delivery_system.repository

import com.teamsparta.delivery_system.domain.entity.Menu
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface MenuRespository: JpaRepository<Menu, Long> {
}