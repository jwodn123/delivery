package com.teamsparta.delivery_system.domain.entity

import com.teamsparta.delivery_system.domain.entity.common.BaseTimeEntity
import com.teamsparta.delivery_system.domain.enums.OrderStatus
import jakarta.persistence.*

@Entity
@Table(name = "orders")
class Order(
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    val member: Member,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", nullable = false)
    val store: Store,

    paymentMethod: String,
    totalPrice: Long = 0,
    requests: String
): BaseTimeEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val orderId: Long? = null

    @Column(name = "payment_method")
    var paymentMethod: String = paymentMethod

    @Column(name = "total_price")
    var totalPrice = totalPrice

    @Column(name = "requests")
    var requests: String = requests

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    var status: OrderStatus = OrderStatus.WAITING_PAY   // 초기 주문 상태 (결제 대기)

}