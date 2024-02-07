package com.teamsparta.delivery_system.exception

import com.teamsparta.delivery_system.web.response.ApiResponseCode

class PaymentException(message: String): BaseException() {
    override var code: ApiResponseCode = ApiResponseCode.PAY_FAILED
    override var message: String = message
}