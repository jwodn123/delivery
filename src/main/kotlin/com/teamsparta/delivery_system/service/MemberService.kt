package com.teamsparta.delivery_system.service

import com.teamsparta.delivery_system.plugin.JwtPlugin
import com.teamsparta.delivery_system.domain.entity.Member
import com.teamsparta.delivery_system.exception.DuplicateException
import com.teamsparta.delivery_system.repository.MemberRepository
import com.teamsparta.delivery_system.web.request.SignUpRequest
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class MemberService(
    private val memberRepository: MemberRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtPlugin: JwtPlugin
) {

    fun signUp(request: SignUpRequest): Member {
        val member = Member(
            useremail = request.useremail,
            password = passwordEncoder.encode(request.password),
            phone = request.phone,
            nickname = request.nickname,
            address = request.address,
            role = request.role
        )
        try {
            return memberRepository.save(member)
        } catch (e: DataIntegrityViolationException) {
            throw DuplicateException("중복된 ID 입니다.")
        }
    }
}