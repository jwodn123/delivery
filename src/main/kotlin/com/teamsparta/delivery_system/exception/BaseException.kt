package com.teamsparta.delivery_system.exception

import com.teamsparta.delivery_system.web.response.ApiResponseCode

abstract class BaseException: RuntimeException() {
    open lateinit var code: ApiResponseCode
    override lateinit var message: String
}