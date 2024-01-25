package com.teamsparta.delivery_system.exception

import com.teamsparta.delivery_system.web.response.ApiResponseCode

class InvalidCredentialException(message: String): BaseException() {
    override var code: ApiResponseCode = ApiResponseCode.FORBIDDEN
    override var message: String = message
}