package com.teamsparta.delivery_system.web.request

import jakarta.validation.constraints.NotBlank

data class CartAddRequest(
    var menuId: Long,
    var quantity: Int
) {
}