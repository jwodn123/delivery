package com.teamsparta.delivery_system.exception

import com.teamsparta.delivery_system.web.response.ApiResponseCode

class DuplicateException(message: String): BaseException() {
    override var code: ApiResponseCode = ApiResponseCode.DUPLICATE_ENTITY
    override var message: String = message
}