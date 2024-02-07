package com.teamsparta.delivery_system.domain.dto.kakao

import com.teamsparta.delivery_system.domain.enums.OrderStatus

/**
 * 결제 취소 요청 시 사용
 */
data class KakaoCancelResponse(
    var aid: String, // 요청 고유 번호
    var tid: String, // 결제 고유 번호
    var cid: String, // 가맹점 코드
    var status: OrderStatus, // 결제상태
    var partner_order_id: Long, // 가맹점 주문 번호
    var partner_user_id: Long, // 가맹점 회원 id
    var payment_method_type: String, // 결제 수단
    var amount: Amount, // 결제 금액 정보
    var approved_cancel_amount: ApprovedCancelAmount, // 이번 요청으로 취소된 금약
    var canceled_amount: CanceledAmount, // 누계 취소 금액
    var cancel_available_amount: CancelAvailableAmount, // 남은 취소 금액
    var item_name: String, // 상품명
    var quantity: Int, // 상품 수량
    var created_at: String, // 결제 요청 시간
    var approved_at: String, // 결제 승인 시간
    var canceled_at: String, // 결제 취소 시간
    var payload: String // 결제 승인 요청에 대해 저장 값, 요청 시 전달 내용

) {
    /**
     * 이번 요청으로 취소된 금액
     */
    data class ApprovedCancelAmount(
        var total: Int, // 이번 요청으로 취소된 전체 금액
        var tax_free: Int, // 이번 요청으로 취소된 비과세 금액
        var vat: Int, // 이번 요청으로 취소된 부가세 금액
        var point: Int, // 이번 요청으로 취소된 포인트 금액
        var discount: Int, // 이번 요청으로 취소된 할인 금액
        var green_deposit: Int // 컵 보증금
    )

    /**
     * 누계 취소 금액
     */
    data class CanceledAmount(
        var total: Int, // 취소된 전체 누적 금액
        var tax_free: Int, // 취소된 비과세 누적 금액
        var vat: Int, // 취소된 부가세 누적 금액
        var point: Int, // 취소된 포인트 누적 금액
        var discount: Int, // 취소된 할인 누적 금액
        var green_deposit: Int // 컵 보증금
    )

    /**
     * 취소 요청 시 전달한 값
     */
    data class CancelAvailableAmount(
        var total: Int, // 전체 취소 가능 금액
        var tax_free: Int, // 취소 가능 비과세 금액
        var vat: Int, // 취소 가능 부가세 금액
        var point: Int, // 취소 가능 포인트 금액
        var discount: Int, // 취소 가능 할인 금액
        var green_deposit: Int // 컵 보증금
    )
}