package com.teamsparta.delivery_system.exception

import com.teamsparta.delivery_system.web.response.ApiResponseCode

class ServerException(message: String): BaseException() {
    override var code: ApiResponseCode = ApiResponseCode.INTERNAL_SERVER_ERROR
    override var message: String = message
}