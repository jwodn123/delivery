package com.teamsparta.delivery_system.integrationtest.service

import com.teamsparta.delivery_system.domain.entity.Cart
import com.teamsparta.delivery_system.domain.entity.CartItem
import com.teamsparta.delivery_system.integrationtest.IntegrationTest
import com.teamsparta.delivery_system.repository.*
import com.teamsparta.delivery_system.service.CartService
import com.teamsparta.delivery_system.web.request.CartAddRequest
import org.springframework.beans.factory.annotation.Autowired
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import org.springframework.transaction.annotation.Transactional


class CartServiceTest: IntegrationTest()  {
    @Autowired
    lateinit var cartService: CartService
    @Autowired
    lateinit var memberRepository: MemberRepository
    @Autowired
    lateinit var cartRepository: CartRepository
    @Autowired
    lateinit var menuRepository: MenuRespository
    @Autowired
    lateinit var cartItemRepository: CartItemRepository
    @Autowired
    lateinit var storeRepository: StoreRepository

    @Test
    @Transactional
    fun 장바구니가_비었을_경우_메뉴담기() {
        // Given
        val memberId = 2L
        val request = CartAddRequest(menuId = 2, quantity = 2)

        // When
        cartService.addCart(memberId, request)

        // Then
        val member = memberRepository.findById(2).orElseThrow()
        val menu = menuRepository.findById(request.menuId).orElseThrow()

        val cart = cartRepository.findByMember(member)
        assertEquals(1, cart?.cartItems?.size)
        assertEquals(menu, cart?.cartItems?.first()?.menu)
        assertEquals(2, cart?.cartItems?.first()?.quantity)
    }

    @Test
    @Transactional
    fun 같은_가게의_다른_메뉴가_담길때_새롭게_메뉴_추가() {
        // Given
        val memberId = 2L
        val menuId = 2L
        val member = memberRepository.findById(memberId).orElseThrow()
        var menu = menuRepository.findById(menuId).orElseThrow()

        // 현재 사용자의 해당 가게의 장바구니에 이미 메뉴가 하나 들어있는 상태로 가정
        val existingCart = cartRepository.save(Cart(member = member, store = menu.store))
        cartItemRepository.save(CartItem(cart = existingCart, menu = menu, quantity = 1))

        // 이미 추가된 메뉴
        val existingMenuId = existingCart.cartItems.firstOrNull()?.menu?.menuId

        // 새롭게 추가할 메뉴
        val newMenuId = 3L
        val request = CartAddRequest(menuId = newMenuId, quantity = 1)

        // When
        cartService.addCart(member.memberId!!, request)

        // Then
        val updatedCart = cartRepository.findByMember(member)
        assertNotNull(updatedCart)
        assertEquals(2, updatedCart?.cartItems?.size)

        // 이미 추가된 메뉴가 존재하는지 확인
        val existingCartItem = updatedCart?.cartItems?.find { it.menu.menuId == existingMenuId }
        assertNotNull(existingCartItem)

        // 새롭게 추가된 메뉴가 존재하는지 확인
        val newCartItem = updatedCart?.cartItems?.find { it.menu.menuId == newMenuId }
        assertNotNull(newCartItem)
        assertEquals(1, newCartItem?.quantity)
    }

    @Test
    @Transactional
    fun 같은_메뉴가_담길때_수량_증가() {
        // Given
        val memberId = 2L
        val menuId = 2L
        val member = memberRepository.findById(memberId).orElseThrow()
        var menu = menuRepository.findById(menuId).orElseThrow()

        // 현재 사용자의 장바구니에 이미 메뉴가 두 개 들어있는 상태로 가정
        val existingCart = cartRepository.save(Cart(member = member, store = menu.store))
        cartItemRepository.save(CartItem(cart = existingCart, menu = menu, quantity = 2))

        // When
        val request = CartAddRequest(menuId = menuId, quantity = 1)
        cartService.addCart(member.memberId!!, request)

        // Then
        val updatedCart = cartRepository.findByMember(member)
        assertNotNull(updatedCart)
        assertEquals(1, updatedCart?.cartItems?.size) // 동일한 메뉴가 중복되지 않고 수량이 증가해야 함
        assertEquals(3, updatedCart?.cartItems?.first()?.quantity) // 수량이 3으로 증가했는지 확인
    }

    @Test
    @Transactional
    fun 이미_장바구니에_메뉴가_있을때_다른가게_메뉴_담을시_기존메뉴_삭제하고_새로운_메뉴추가() {
        // Given
        val memberId = 2L
        val menuId = 2L
        val member = memberRepository.findById(memberId).orElseThrow()
        var menu = menuRepository.findById(menuId).orElseThrow()

        // 현재 사용자의 장바구니에 이미 메뉴가 들어있는 상태로 가정
        val existingCart = cartRepository.save(Cart(member = member, store = menu.store))
        cartItemRepository.save(CartItem(cart = existingCart, menu = menu, quantity = 1))

        // When
        // 다른 가게의 메뉴를 추가
        val diffStoreMenuId = 1L
        val request = CartAddRequest(menuId = diffStoreMenuId, quantity = 1)
        cartService.addCart(member.memberId!!, request)

        // Then
        val updatedCart = cartRepository.findByMember(member)
        assertNotNull(updatedCart)
        assertNull(updatedCart?.cartItems?.find { it.menu.menuId == menuId }) // 기존 메뉴가 삭제되었는지 확인
        assertNotNull(updatedCart?.cartItems?.find { it.menu.menuId == diffStoreMenuId }) // 새로운 메뉴가 추가되었는지 확인
    }


}