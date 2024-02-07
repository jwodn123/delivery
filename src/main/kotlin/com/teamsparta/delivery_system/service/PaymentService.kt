package com.teamsparta.delivery_system.service

import com.teamsparta.delivery_system.domain.dto.PayApproveResDto
import com.teamsparta.delivery_system.domain.dto.kakao.KakaoCancelResponse
import com.teamsparta.delivery_system.domain.dto.PayReadyResDto
import com.teamsparta.delivery_system.domain.enums.OrderStatus
import com.teamsparta.delivery_system.exception.BadRequestException
import com.teamsparta.delivery_system.exception.NotFoundException
import com.teamsparta.delivery_system.repository.CartItemRepository
import com.teamsparta.delivery_system.repository.MemberRepository
import com.teamsparta.delivery_system.repository.OrderRepository
import com.teamsparta.delivery_system.web.request.PayCompleteRequest
import com.teamsparta.delivery_system.web.request.PayReadyRequest
import jakarta.servlet.http.HttpSession
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.*
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import org.springframework.web.client.RestTemplate
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import kotlin.concurrent.thread

@Service
@Transactional
class PaymentService(
    private val memberRepository: MemberRepository,
    private val orderRepository: OrderRepository,
    @Value("\${CID}")
    private val cid: String,
    @Value("\${ADMIN_KEY}")
    private val admin_Key: String,
) {

    @Transactional
    fun kakaoPayReady(memberId: Long, request: PayReadyRequest): PayReadyResDto {
        var member = memberRepository.findById(memberId).orElseThrow { NotFoundException("회원정보를 찾을 수 없습니다.") }
        var order = orderRepository.findById(request.orderId).orElseThrow { NotFoundException("주문정보를 찾을 수 없습니다.") }

        if (order.status != OrderStatus.WAITING_PAY) {
            throw BadRequestException("결제 대기 상태가 아닙니다!!!")
        }

        // 카카오페이 요청 양식
        val parameters: MultiValueMap<String, Any> = LinkedMultiValueMap()
        parameters.add("cid", cid)
        parameters.add("partner_order_id", request.orderId)
        parameters.add("partner_user_id", request.memberId)
        parameters.add("item_name", request.itemName)
        parameters.add("quantity", request.quantity)
        parameters.add("total_amount", request.totalPrice)
        parameters.add("tax_free_amount", "0");
        parameters.add("approval_url", "http://localhost:8080/payment/success") // 성공 시 redirect url
        parameters.add("cancel_url", "http://localhost:8080/payment/cancel") // 취소 시 redirect url
        parameters.add("fail_url", "http://localhost:8080/payment/fail") // 실패 시 redirect url

        // 파라미터, 헤더
        val requestEntity: HttpEntity<MultiValueMap<String, Any>> = HttpEntity(parameters, getHeaders())

        // 외부에 보낼 url
        val restTemplate = RestTemplate()
        val payReadyResDto: PayReadyResDto = restTemplate.postForObject(
            "https://kapi.kakao.com/v1/payment/ready",
            requestEntity,
            PayReadyResDto::class.java
        )!!

        return payReadyResDto

    }

    /**
     * 결제 완료
     */
    fun approveResponse(memberId: Long, pgToken: String, request: PayCompleteRequest): PayApproveResDto {
        var member = memberRepository.findById(memberId).orElseThrow { NotFoundException("회원정보를 찾을 수 없습니다.") }

        // 카카오 요청
        val parameters: MultiValueMap<String, Any> = LinkedMultiValueMap()
        parameters.add("cid", cid)
        parameters.add("tid", "tid")
        parameters.add("partner_order_id", request.orderId)
        parameters.add("partner_user_id", request.memberId)
        parameters.add("pg_token", pgToken)

        val requestEntity: HttpEntity<MultiValueMap<String, Any>> = HttpEntity(parameters, getHeaders())

        val restTemplate = RestTemplate()

        val payApproveResDto: PayApproveResDto = restTemplate.postForObject(
            "https://kapi.kakao.com/v1/payment/approve",
            requestEntity,
            PayApproveResDto::class.java
        )!!

        return payApproveResDto
    }

    /**
     * 결제 환불
     */
    fun kakaoCancel(): KakaoCancelResponse {
        // 카카오페이 요청
        val parameters: MultiValueMap<String, String> = LinkedMultiValueMap()
        parameters.add("cid", cid)
        parameters.add("tid", "환불할 결제 고유 번호")
        parameters.add("cancel_amount", "환불 금액")
        parameters.add("cancel_tax_free_amount", "환불 비과세 금액")
        parameters.add("cancel_vat_amount", "환불 부가세")

        // 파라미터, 헤더
        val requestEntity: HttpEntity<MultiValueMap<String, String>> = HttpEntity(parameters, getHeaders())

        // 외부에 보낼 url
        val restTemplate = RestTemplate()

        return restTemplate.postForObject(
            "https://kapi.kakao.com/v1/payment/cancel",
            requestEntity,
            KakaoCancelResponse::class.java
        ) ?: throw IllegalStateException("KakaoCancelResponse is null")


    }



    /**
     * 카카오 요구 헤더값
     */
    private fun getHeaders(): HttpHeaders {
        val httpHeaders = HttpHeaders()

        val auth = "KakaoAK $admin_Key"

        httpHeaders.set("Authorization", auth)
        httpHeaders.set("Content-type","application/x-www-form-urlencoded;charset=utf-8")

        return httpHeaders
    }
}
