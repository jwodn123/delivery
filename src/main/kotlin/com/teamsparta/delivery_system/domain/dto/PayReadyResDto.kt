package com.teamsparta.delivery_system.domain.dto

import org.springframework.stereotype.Component

/**
 *
 * 결제 요청 시 카카오에게 받음
 */
data class PayReadyResDto(
    val tid: String, // 결제 고유번호
    var next_redirect_pc_url: String, // pc 웹일 경우 받는 결제 페이지
    var created_at: String,
    //var pgToken: String
) {
    companion object {
        fun ready(payReadyResDto: PayReadyResDto): PayReadyResDto {
            return PayReadyResDto(
                tid = payReadyResDto.tid,
                next_redirect_pc_url = payReadyResDto.next_redirect_pc_url,
                created_at = payReadyResDto.created_at,
                //pgToken = payReadyResDto.pgToken
            )
        }
    }
}
