package com.teamsparta.delivery_system.service

import com.teamsparta.delivery_system.plugin.JwtPlugin
import com.teamsparta.delivery_system.domain.enums.MemberRole
import com.teamsparta.delivery_system.repository.MemberRepository
import com.teamsparta.delivery_system.web.request.SignUpRequest
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Test
import org.springframework.security.crypto.password.PasswordEncoder


class MemberServiceTest {

    private val memberRepository: MemberRepository = mockk()
    private val passwordEncoder: PasswordEncoder = mockk()
    private val jwtPlugin: JwtPlugin = mockk()
    private val memberService: MemberService = MemberService(memberRepository, passwordEncoder, jwtPlugin)

    @Test
    fun `사용자 회원가입`() {
        // Given
        val request = SignUpRequest(useremail = "testUser", password = "testPassword",
            phone = "01012345678", nickname = "jjw", address = "서울시 구로구", role = MemberRole.CUSTOMER)
        val encodedPassword = "encodedTestPassword" // 암호화된 비밀번호 예시

        every { passwordEncoder.encode(any()) } returns encodedPassword
        every { memberRepository.save(any()) } returns mockk()

        // When
        val result = memberService.signUp(request)

        // Then

    }
}