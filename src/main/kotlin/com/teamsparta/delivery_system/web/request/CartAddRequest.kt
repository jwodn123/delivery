package com.teamsparta.delivery_system.web.request

import jakarta.validation.constraints.NotBlank

data class CartAddRequest(
    
    var quantity: Int

) {
}