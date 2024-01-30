package com.teamsparta.delivery_system.domain.dto

import com.teamsparta.delivery_system.domain.entity.Menu
import com.teamsparta.delivery_system.domain.entity.Store
import com.teamsparta.delivery_system.domain.enums.Category
import jakarta.persistence.Column
import java.time.LocalDateTime

data class MenuDto(

    val menuId: Long? = null,
    var category: Category,
    var name: String,
    var explanation: String,
    var price: Long

) {

    companion object {
        fun fromEntity(menu: Menu): MenuDto {
            val dto = MenuDto(
                menuId = menu.menuId,
                category = menu.category,
                name = menu.name,
                explanation = menu.explanation,
                price = menu.price
            )
            return dto
        }

        fun fromEntities(menus: List<Menu>): List<MenuDto> {
            return menus.map {
                val dto = MenuDto(
                    menuId = it.menuId,
                    category = it.category,
                    name = it.name,
                    explanation = it.explanation,
                    price = it.price
                )

                dto
            }
        }
    }
}