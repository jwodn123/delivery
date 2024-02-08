package com.teamsparta.delivery_system.domain.dto

import com.teamsparta.delivery_system.domain.dto.Amount

/**
 * 결제 승인 요청 시 사용
 */
data class PayApproveResDto(

    var amount: Amount, // 결제 금액 정보
    var item_name: String, // 상품명
    var created_at: String, // 결제 요청 시간
    var approved_at: String, // 결제 승인 시간
) {

    companion object {
        fun approve(payApproveResDto: PayApproveResDto): PayApproveResDto {
            return PayApproveResDto(
                amount = payApproveResDto.amount,
                item_name = payApproveResDto.item_name,
                created_at = payApproveResDto.created_at,
                approved_at = payApproveResDto.approved_at
            )
        }
    }
}