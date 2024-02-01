package com.teamsparta.delivery_system.web.request

import com.teamsparta.delivery_system.domain.enums.OrderStatus
import jakarta.validation.constraints.NotBlank

data class OrderRequest(

    var cartId: Long,
    @field:NotBlank
    var paymentMethod: String,
    var totalPrice: Long,
    var requests: String,

) {
}