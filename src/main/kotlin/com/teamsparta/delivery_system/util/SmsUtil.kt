package com.teamsparta.delivery_system.util

import jakarta.annotation.PostConstruct
import net.nurigo.sdk.NurigoApp.initialize
import net.nurigo.sdk.message.model.Message
import net.nurigo.sdk.message.model.MessageType
import net.nurigo.sdk.message.request.SingleMessageSendingRequest
import net.nurigo.sdk.message.response.SingleMessageSentResponse
import net.nurigo.sdk.message.service.DefaultMessageService
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component


@Component
class SmsUtil {
    @Value("\${coolsms.api.key}")
    private val apiKey: String? = null

    @Value("\${coolsms.api.secret}")
    private val apiSecretKey: String? = null

    private var messageService: DefaultMessageService? = null

    // @PostConstruct 어노테이션이 부여된 init 메서드는 빈이 생성된 후에 자동으로 실행되는 메서드
    @PostConstruct
    private fun init() {
        this.messageService = initialize(apiKey!!, apiSecretKey!!, "https://api.coolsms.co.kr")
    }

    // 단일 메시지 발송
    fun sendOne(to: String?, message: String, ): SingleMessageSentResponse? {
        val smsMessage = Message(
            type = MessageType.SMS,
            from = "01037286657", //coolSMS 계정에 등록된 내 발신번호
            to = to,
            text = message
        )

        return messageService?.sendOne(SingleMessageSendingRequest(smsMessage))
    }
}