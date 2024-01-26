package com.teamsparta.delivery_system.service

import com.teamsparta.delivery_system.controller.MemberController
import com.teamsparta.delivery_system.plugin.JwtPlugin
import com.teamsparta.delivery_system.domain.enums.MemberRole
import com.teamsparta.delivery_system.repository.MemberRepository
import com.teamsparta.delivery_system.web.request.LoginRequest
import com.teamsparta.delivery_system.web.request.SignUpRequest
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.springframework.security.crypto.password.PasswordEncoder
import org.junit.jupiter.api.Assertions.assertEquals
import org.springframework.http.HttpStatus


class MemberServiceTest {

    private val memberRepository: MemberRepository = mockk()
    private val passwordEncoder: PasswordEncoder = mockk()
    private val jwtPlugin: JwtPlugin = mockk()
    private val memberService: MemberService = MemberService(memberRepository, passwordEncoder, jwtPlugin)

    @Test
    fun 사용자_회원가입 () {
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

    @Test
    fun `올바른 자격 증명으로 로그인 성공`() {
        // Given
        val request = LoginRequest(useremail = "test@example.com", password = "password123")
        val memberService = mock(MemberService::class.java)
        `when`(memberService.login(request)).thenReturn("mocked_token")
        val controller = MemberController(memberService)

        // When
        val response = controller.login(request)

        // Then
        assertEquals("mocked_token", response.body?.data) // 예상되는 토큰을 반환하는지 확인합니다.
        assertEquals("로그인 성공", response.body?.message) // 예상되는 메시지를 반환하는지 확인합니다.
        assertEquals(HttpStatus.OK, response.statusCode) // 상태 코드가 200인지 확인합니다.
    }

}