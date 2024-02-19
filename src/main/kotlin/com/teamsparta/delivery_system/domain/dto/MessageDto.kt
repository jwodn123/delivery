package com.teamsparta.delivery_system.domain.dto

import com.teamsparta.delivery_system.domain.entity.Cart
import com.teamsparta.delivery_system.domain.enums.OrderStatus



data class MessageDto(
    val memberId: Long,
    val name: String,
    val phoneNumber: String,
    val status: OrderStatus,
) {

    companion object {
        fun fromEntity(messageDto: MessageDto): MessageDto {
            val dto = MessageDto(
                memberId = messageDto.memberId,
                name = messageDto.name,
                phoneNumber = messageDto.phoneNumber,
                status = messageDto.status
            )
            return dto
        }

    }
}