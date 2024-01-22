package com.teamsparta.delivery_system.exception

import com.teamsparta.delivery_system.web.response.ApiResponseCode

class NotFoundException(message: String): BaseException() {
    override var code: ApiResponseCode = ApiResponseCode.NOT_FOUND
    override var message: String = message
}