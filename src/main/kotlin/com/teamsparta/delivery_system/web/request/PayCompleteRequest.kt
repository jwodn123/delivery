package com.teamsparta.delivery_system.web.request

import jakarta.validation.constraints.NotBlank

data class PayCompleteRequest(

    var orderId: Long,
    var memberId: Long,
//    @field:NotBlank
//    var pgToken: String
)