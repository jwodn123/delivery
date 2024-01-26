package com.teamsparta.delivery_system.web.request

import jakarta.validation.constraints.NotBlank

data class LoginRequest(
    @field:NotBlank
    var useremail: String,

    @NotBlank
    var password: String
)