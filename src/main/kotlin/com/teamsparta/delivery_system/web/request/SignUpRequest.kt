package com.teamsparta.delivery_system.web.request

import com.teamsparta.delivery_system.domain.enums.MemberRole
import jakarta.validation.constraints.*

data class SignUpRequest(
    @field:NotBlank
    @field:Email(message = "이메일 형식이 아닙니다.")
    var userid: String,

    @field:NotBlank
    var password: String,

    @field:NotNull
    var role: MemberRole,

)