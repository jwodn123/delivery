package com.teamsparta.delivery_system.domain.enums
enum class OrderStatus(val value: String) {
    WAITING_PAY("결제대기"),
    COMPLETE_PAY("결제완료"),

    CONFIRMED("주문확정"),
    CANCEL("주문취소"),

    DE_START("배달시작"),
    DE_ING("배달중"),
    DE_FINISH("배달완료");

    fun isSendable(): Boolean {
        return this == CONFIRMED || this == DE_START || this == DE_FINISH
    }

}