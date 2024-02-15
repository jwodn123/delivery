package com.teamsparta.delivery_system.domain.entity

import com.teamsparta.delivery_system.domain.entity.common.BaseTimeEntity
import com.teamsparta.delivery_system.domain.enums.OrderStatus
import com.teamsparta.delivery_system.domain.enums.PaymentMethod
import com.teamsparta.delivery_system.exception.NotFoundException
import jakarta.persistence.*
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

@Entity
@Table(name = "orders")
class Order(
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    val member: Member, // 일반 사용자

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", nullable = false)
    val store: Store,

    @Column(name = "payment_method")
    @Enumerated(EnumType.STRING)
    var paymentMethod: PaymentMethod,

    @Column(name = "total_price")
    var totalPrice: Int = 0,

    @Column(name = "requests")
    var requests: String? = null, // 요청사항은 선택 입력

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    var status: OrderStatus = OrderStatus.WAITING_PAY,   // 초기 주문 상태 (결제 대기)

): BaseTimeEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val orderId: Long? = null

    @OneToMany(mappedBy = "order", cascade = [CascadeType.ALL])
    var orderItems: MutableList<OrderItem> = mutableListOf()

    fun confirm(storeId: Long) {
        if (this.store.storeId != storeId) {
            throw NotFoundException("해당 매장의 주문이 아닙니다.")
        } else if(this.status == OrderStatus.CONFIRMED) {
            // 주문 상태를 "배달시작" 으로 변경
            this.status = OrderStatus.DE_START
            return
        } else if(this.status == OrderStatus.DE_START) {
            // 주문 상태를 "배달완료" 로 변경
            this.status = OrderStatus.DE_FINISH
            return
        }

        // 주문 상태를 "주문확정" 으로 변경
        this.status = OrderStatus.CONFIRMED
    }

}