package com.teamsparta.delivery_system.web.request

import jakarta.validation.constraints.NotBlank

data class PayReadyRequest(

    var orderId: Long,
    var memberId: Long,
    @field:NotBlank
    var itemName: String,
    @field:NotBlank
    var quantity: Int,
    @field:NotBlank
    var totalPrice: Int

)