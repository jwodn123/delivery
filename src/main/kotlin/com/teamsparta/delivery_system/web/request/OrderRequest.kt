package com.teamsparta.delivery_system.web.request

import com.teamsparta.delivery_system.domain.enums.OrderStatus
import com.teamsparta.delivery_system.domain.enums.PaymentMethod
import jakarta.validation.constraints.NotBlank

data class OrderRequest(

    var cartId: Long,
    var paymentMethod: PaymentMethod,
    var requests: String,

) {
}