package com.teamsparta.delivery_system.domain.dto

import com.teamsparta.delivery_system.domain.entity.Menu
import com.teamsparta.delivery_system.domain.entity.Store
import java.time.LocalDateTime

data class StoreDto(

    var storeId: Long? = null,
    var name: String,
    var address: String,
    var content: String,
    var phone: String,
    var minDeliveryPrice: Int,
    var deliveryTip: Int,
    var minDeliveryTime: Int,
    var maxDeliveryTime: Int,
    val menus: MutableList<MenuDto> = mutableListOf()


) {

    companion object {
        fun fromEntity(store: Store): StoreDto {
            val menuDtos = store.menus.map { MenuDto.fromEntity(it) }.toMutableList()

            val dto = StoreDto(
                storeId = store.storeId,
                name = store.name,
                address = store.address,
                content = store.content,
                phone = store.phone,
                minDeliveryPrice = store.minDeliveryPrice,
                deliveryTip = store.deliveryTip,
                minDeliveryTime = store.minDeliveryTime,
                maxDeliveryTime = store.maxDeliveryTime,
                menus = menuDtos
            )

            return dto
        }

        fun fromEntities(stores: List<Store>): List<StoreDto> {
            return stores.map { store ->
                val menuDtos = store.menus.map { MenuDto.fromEntity(it) }.toMutableList()

                StoreDto(
                    storeId = store.storeId,
                    name = store.name,
                    address = store.address,
                    content = store.content,
                    phone = store.phone,
                    minDeliveryPrice = store.minDeliveryPrice,
                    deliveryTip = store.deliveryTip,
                    minDeliveryTime = store.minDeliveryTime,
                    maxDeliveryTime = store.maxDeliveryTime,
                    menus = menuDtos
                )
            }
        }
    }
}