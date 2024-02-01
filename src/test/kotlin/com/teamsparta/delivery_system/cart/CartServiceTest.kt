//import com.teamsparta.delivery_system.domain.entity.Cart
//import com.teamsparta.delivery_system.domain.entity.Member
//import com.teamsparta.delivery_system.domain.entity.Menu
//import com.teamsparta.delivery_system.domain.entity.Store
//import com.teamsparta.delivery_system.domain.enums.Category
//import com.teamsparta.delivery_system.domain.enums.MemberRole
//import com.teamsparta.delivery_system.repository.CartRepository
//import com.teamsparta.delivery_system.repository.MemberRepository
//import com.teamsparta.delivery_system.repository.MenuRespository
//import com.teamsparta.delivery_system.service.CartService
//import com.teamsparta.delivery_system.web.request.CartAddRequest
//import io.mockk.every
//import io.mockk.mockk
//import io.mockk.verify
//import org.junit.jupiter.api.Assertions.assertEquals
//import org.junit.jupiter.api.Test
//import java.util.*
//
//class CartServiceTest {
//
//    private val memberRepository: MemberRepository = mockk()
//    private val cartRepository: CartRepository = mockk()
//    private val menuRepository: MenuRespository = mockk()
//
//    private val cartService = CartService(memberRepository, cartRepository, menuRepository)
//
//    private fun createMember(): Member {
//        return Member(
//            useremail = "jw@naver.com",
//            password = "123456789",
//            phone = "010-1234-2345",
//            nickname = "JW",
//            address = "서울시 강남구",
//            role = MemberRole.CUSTOMER
//        )
//    }
//
//    private fun createStore(member: Member): Store {
//        return Store(
//            member = member,
//            name = "BBQ 강남역점",
//            address = "서울시 강남구",
//            content = "bbq 강남역점입니다.",
//            phone = "02-123-1234",
//            minDeliveryPrice = 20000,
//            deliveryTip = 3000,
//            minDeliveryTime = 10,
//            maxDeliveryTime = 20
//        )
//    }
//
//    private fun createMenu(store: Store): Menu {
//        return Menu(
//            store = store,
//            category = Category.POPULAR,
//            name = "황금 올리브 치킨",
//            explanation = "신선한 기름으로 튀긴 치킨",
//            price = 20000
//        )
//    }
//
//    @Test
//    fun `새로운_메뉴_장바구니_담기`() {
//        // given
//        val memberId = 1L
//        val menuId = 1L
//        val request = CartAddRequest(menuId = menuId, quantity = 2)
//        val member = createMember()
//        val store = createStore(member)
//        val menu = createMenu(store)
//        val cart =  Cart(menu = menu, quantity = 2, member = member)
//
//        every { memberRepository.findById(memberId) } returns Optional.of(member)
//        every { menuRepository.findById(menuId) } returns Optional.of(menu)
//        every { cartRepository.findByMemberAndMenu(member, menu) } returns null
//        every { cartRepository.save(any<Cart>()) } returns cart
//
//        // when
//        val result = cartService.addCart(memberId, request)
//
//        // then
//        assertEquals(2, result.quantity)
//        verify { cartRepository.save(any<Cart>()) }
//    }
//
//    @Test
//    fun `중복된_메뉴_장바구니_담기`() {
//        // given
//        val memberId = 1L
//        val menuId = 1L
//        val request = CartAddRequest(menuId = menuId, quantity = 2)
//        val member = createMember()
//        val store = createStore(member)
//        val menu = createMenu(store)
//        val cart =  Cart(menu = menu, quantity = 2, member = member)
//
//        every { memberRepository.findById(memberId) } returns Optional.of(member)
//        every { menuRepository.findById(menuId) } returns Optional.of(menu)
//        every { cartRepository.findByMemberAndMenu(member, menu) } returns cart
//
//        // when
//        val result = cartService.addCart(memberId, request)
//
//        // then
//        assertEquals(cart.quantity + request.quantity , result.quantity)
//        verify { cartRepository.save(any<Cart>()) } . 0
//    }
//
//    @Test
//    fun `장바구니_리스트`() {
//        // given
//        val member = createMember()
//        val store = createStore(member)
//
//        val carts = listOf(
//            Cart(menu = createMenu(store), quantity = 1),
//            Cart(menu = createMenu(store), quantity = 2)
//        )
//        every { cartRepository.findAll() } returns carts
//
//        // when
//        val result = cartService.getCartList()
//
//        // then
//        assertEquals(2, result.size)
//    }
//
//    @Test
//    fun `장바구니_메뉴_삭제`() {
//        // given
//        val memberId = 1L
//        val cartId = 1L
//        val menuId = 1L
//        val member = createMember()
//        val store = createStore(member)
//        val menu = createMenu(store)
//        val cart = Cart(menu = menu, quantity = 2)
//
//        every { memberRepository.findById(memberId) } returns java.util.Optional.of(member)
//        every { menuRepository.findById(menuId) } returns java.util.Optional.of(menu)
//        every { cartRepository.findById(cartId) } returns java.util.Optional.of(cart)
//
//        every { cartRepository.delete(cart) } answers { }
//
//        // when
//        cartService.deleteCartMenu(memberId, cartId, menuId)
//
//        // then
//        verify { cartRepository.delete(cart) }
//    }
//}
