package com.teamsparta.delivery_system.web.request

import com.teamsparta.delivery_system.domain.dto.StoreDto
import jakarta.validation.constraints.*

data class StoreCreateRequest (

    @field:NotBlank
    var name: String,

    @field:NotBlank
    var address: String,

    @field:NotBlank
    var content: String,

    @field:NotBlank
    var phone: String,

    var minDeliveryPrice: Int,
    var deliveryTip: Int,
    var minDeliveryTime: Int,
    var maxDeliveryTime: Int
) {
//    fun toServiceDto(): StoreDto {
//        return StoreDto(
//            name = name,
//            address = address,
//            content = content,
//            phone = phone,
//            minDeliveryPrice = minDeliveryPrice,
//            deliveryTip = deliveryTip,
//            minDeliveryTime = minDeliveryTime,
//            maxDeliveryTime = maxDeliveryTime,
//        )
//    }

}