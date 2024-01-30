package com.teamsparta.delivery_system.web.request

import com.teamsparta.delivery_system.domain.dto.MenuDto
import com.teamsparta.delivery_system.domain.enums.Category
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class MenuUpdateRequest(

    var category: Category,

    @field:NotBlank
    var name: String,

    @field:NotBlank
    var explanation: String,

    var price: Long,

) {

//    fun toServiceDto(): MenuDto {
//        return MenuDto(
//            menuId = menuId,
//            category = category,
//            name = name,
//            explanation = explanation,
//            price = price
//        )
//    }
}