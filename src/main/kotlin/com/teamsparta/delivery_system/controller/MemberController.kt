package com.teamsparta.delivery_system.controller

import com.teamsparta.delivery_system.service.MemberService
import com.teamsparta.delivery_system.web.request.LoginRequest
import com.teamsparta.delivery_system.web.request.SignUpRequest
import com.teamsparta.delivery_system.web.response.SingleResponse
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RequestMapping("/users")
@RestController
class MemberController(
    private val memberService: MemberService
) {
    @PostMapping("/signup")
    fun signUp(@RequestBody @Valid request: SignUpRequest): ResponseEntity<SingleResponse<String>> {
        memberService.signUp(request)
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(SingleResponse.success("회원가입 성공"))
    }

    @PostMapping("/login")
    fun login(@RequestBody @Valid request: LoginRequest): ResponseEntity<SingleResponse<String>> {
        val token = memberService.login(request)
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(SingleResponse.successOf("로그인 성공", token))
    }
}