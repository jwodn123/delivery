package com.teamsparta.delivery_system.domain.entity

import com.teamsparta.delivery_system.domain.entity.common.BaseTimeEntity
import com.teamsparta.delivery_system.domain.enums.OrderStatus
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "orders")
class Order(
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    val member: Member,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id", nullable = false)
    val menu: Menu,

    @Column(name = "payment_method")
    var paymentMethod: String,

    @Column(name = "total_price")
    var totalPrice: Long = 0,

    @Column(name = "requests")
    var requests: String? = null, // 요청사항은 선택 입력

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    var status: OrderStatus = OrderStatus.WAITING_PAY,   // 초기 주문 상태 (결제 대기)

    @Column(name = "order_date", nullable = false)  // 추가: 주문 일자
    var orderDate: LocalDateTime = LocalDateTime.MIN

): BaseTimeEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val orderId: Long? = null

}