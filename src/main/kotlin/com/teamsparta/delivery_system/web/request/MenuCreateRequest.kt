package com.teamsparta.delivery_system.web.request

import com.teamsparta.delivery_system.domain.enums.Category
import jakarta.validation.constraints.NotBlank

data class MenuCreateRequest (

    var category: Category,

    @field:NotBlank
    var name: String,

    @field:NotBlank
    var explanation: String,

    var price: Long,

) {
//    fun toServiceDto(): MenuDto {
//        return MenuDto(
//            category = category,
//            name = name,
//            explanation = explanation,
//            price = price
//        )
//    }

}