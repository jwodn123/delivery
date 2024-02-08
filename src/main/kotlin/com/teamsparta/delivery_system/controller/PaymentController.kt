package com.teamsparta.delivery_system.controller

import com.teamsparta.delivery_system.web.response.KakaoCancelResponse
import com.teamsparta.delivery_system.domain.dto.PayReadyResDto
import com.teamsparta.delivery_system.exception.PaymentException
import com.teamsparta.delivery_system.service.PaymentService
import com.teamsparta.delivery_system.web.request.PayReadyRequest
import com.teamsparta.delivery_system.web.response.SingleResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.User
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/payment")
class PaymentController(
    private val paymentService: PaymentService,
    private val cartService: PaymentService
) {

    /**
     * 결제요청
     */
    @PostMapping("/ready")
    fun readyToKakaoPay(
        @AuthenticationPrincipal user: User,
        @RequestBody request: PayReadyRequest
    ): ResponseEntity<SingleResponse<PayReadyResDto>> {
        val kakaoReadyResponse = paymentService.kakaoPayReady(user.username.toLong(), request)
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(SingleResponse.successOf("결제 요청 성공!", PayReadyResDto.ready(kakaoReadyResponse)))
    }

    /**
     * 결제 진행 중 취소
     */
    @GetMapping("/cancel")
    fun cancel() {
        throw PaymentException("결제 취소!")
    }

    /**
     * 결제 실패
     */
    @GetMapping("/fail")
    fun fail() {
        throw PaymentException("결제 실패!")
    }

    /**
     * 환불
     */
    @PostMapping("/refund")
    fun refund(): ResponseEntity<KakaoCancelResponse> {
        val kakaoCancelResponse = paymentService.kakaoCancel()
        return ResponseEntity.ok(kakaoCancelResponse)
    }
}