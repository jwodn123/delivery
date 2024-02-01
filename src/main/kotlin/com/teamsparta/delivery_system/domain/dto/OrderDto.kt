package com.teamsparta.delivery_system.domain.dto

import com.teamsparta.delivery_system.domain.entity.Order
import com.teamsparta.delivery_system.domain.enums.OrderStatus

data class OrderDto(
    val orderId: Long? = null,
    var paymentMethod: String,
    var totalPrice: Long,
    var requests: String? = null,
    var status: OrderStatus
) {

    companion object {
        fun fromEntity(order: Order): OrderDto {
            val dto = OrderDto(
                orderId = order.orderId,
                paymentMethod = order.paymentMethod,
                totalPrice = order.totalPrice,
                requests = order.requests,
                status = order.status
            )
            return dto
        }

        fun customerOrderList(orders: List<Order>): List<OrderDto> {
            return orders.map {
                val dto = OrderDto(
                    orderId = it.orderId,
                    paymentMethod = it.paymentMethod,
                    totalPrice = it.totalPrice,
                    status = it.status
                )

                dto
            }
        }

        fun ownerOrderList(orders: List<Order>): List<OrderDto> {
            return orders.map {
                val dto = OrderDto(
                    orderId = it.orderId,
                    paymentMethod = it.paymentMethod,
                    totalPrice = it.totalPrice,
                    requests = it.requests,
                    status = it.status
                )

                dto
            }
        }
    }
}