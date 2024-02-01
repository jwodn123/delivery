//import com.teamsparta.delivery_system.domain.entity.Cart
//import com.teamsparta.delivery_system.domain.entity.Member
//import com.teamsparta.delivery_system.domain.enums.OrderStatus
//import com.teamsparta.delivery_system.exception.NotFoundException
//import com.teamsparta.delivery_system.repository.*
//import com.teamsparta.delivery_system.service.CartService
//import com.teamsparta.delivery_system.service.OrderService
//import com.teamsparta.delivery_system.web.request.OrderRequest
//import io.mockk.mockk
//import org.junit.jupiter.api.Test
//import org.junit.jupiter.api.Assertions.assertEquals
//import org.junit.jupiter.api.Assertions.assertNotNull
//import org.mockito.Mock
//import io.mockk.every
//import org.junit.jupiter.api.Order
//
//
//class OrderServiceTest {
//
//    val cartServiceTest = CartServiceTest()
//
//    private val memberRepository: MemberRepository = mockk()
//    private val cartRepository: CartRepository = mockk()
//    private val menuRepository: MenuRespository = mockk()
//    private val orderRepository: OrderRepository = mockk()
//    private val storeRepository: StoreRepository = mockk()
//
//    private val cartService = CartService(memberRepository, cartRepository, menuRepository)
//    private val orderService = OrderService(orderRepository, cartRepository, memberRepository, storeRepository)
//
//    @Test
//    fun `장바구니_메뉴_주문`() {
//        // given
//        cartServiceTest.장바구니에_메뉴_담기()
//
//        // when
//        orderService.cartOrder
//        val response = OrderSteps.placeOrderRequest(1L, OrderSteps.createOrderRequest())
//
//        // then
//        val orderId = response.path<Long>("orderId")
//
//        // 생성된 주문의 상태를 확인
//        val order = orderRepository.findById(orderId).orElseThrow { NotFoundException("주문을 찾을 수 없습니다.") }
//        assertNotNull(order)
//        assertEquals(OrderStatus.WAITING_PAY, order.status)
//
//    }
//
//
////    @Test
////    fun `주문시_장바구니에_메뉴가_없으면_에러발생`() {
////        // given
////        val emptyCartId = 2L
////
////        // when, then
////        assertThrows<NotFoundException> {
////            OrderSteps.placeOrderRequest(emptyCartId, OrderSteps.createOrderRequest())
////        }
////    }
//}