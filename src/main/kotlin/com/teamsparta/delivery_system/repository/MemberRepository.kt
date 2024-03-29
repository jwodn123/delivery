package com.teamsparta.delivery_system.repository

import com.teamsparta.delivery_system.domain.entity.Member
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface MemberRepository: JpaRepository<Member, Long> {
    fun findByUseremail(useremail: String) : Member?
}