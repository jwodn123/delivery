package com.teamsparta.delivery_system.exception

import com.teamsparta.delivery_system.web.response.ApiResponseCode

class JwtException(
    override var code: ApiResponseCode = ApiResponseCode.UNAUTHORIZED,
    override var message: String
): BaseException()