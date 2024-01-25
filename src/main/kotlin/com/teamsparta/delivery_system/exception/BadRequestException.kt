package com.teamsparta.delivery_system.exception

import com.teamsparta.delivery_system.web.response.ApiResponseCode

class BadRequestException(message: String): BaseException() {
    override var code: ApiResponseCode = ApiResponseCode.BAD_REQUEST
    override var message: String = message
}