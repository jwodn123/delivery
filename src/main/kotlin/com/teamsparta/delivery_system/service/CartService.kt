package com.teamsparta.delivery_system.service

import com.teamsparta.delivery_system.domain.dto.CartDto
import com.teamsparta.delivery_system.domain.entity.Cart
import com.teamsparta.delivery_system.domain.entity.CartItem
import com.teamsparta.delivery_system.domain.entity.Member
import com.teamsparta.delivery_system.domain.entity.Menu
import com.teamsparta.delivery_system.exception.NotFoundException
import com.teamsparta.delivery_system.repository.CartItemRepository
import com.teamsparta.delivery_system.repository.CartRepository
import com.teamsparta.delivery_system.repository.MemberRepository
import com.teamsparta.delivery_system.repository.MenuRespository
import com.teamsparta.delivery_system.web.request.CartAddRequest
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service


@Service
class CartService(
    private val memberRepository: MemberRepository,
    private val cartRepository: CartRepository,
    private val menuRepository: MenuRespository,
    private val cartItemRepository: CartItemRepository
) {

    // 장바구니에 메뉴 담기
    @Transactional
    fun addCart(memberId: Long, request: CartAddRequest) {
        val (member, menu) = findMemberAndMenu(memberId, request.menuId)

        // member에 해당하는 모든 장바구니 가져오기
        val cart = cartRepository.findByMember(member)
        val quantity = request.quantity

        // 기존 장바구니가 비어있을 경우
        if (cart == null) {
            val newCart = cartRepository.save(Cart(member = member, store = menu.store))
            cartItemRepository.save(CartItem(cart = newCart, menu = menu, quantity = quantity))
        } else {
            // 장바구니에 담긴 메뉴의 StoreId와 새로 추가되는 메뉴의 StoreId가 같을 때,
            if (cart.isSameStore(menu)) {
                val cartItem = cart.cartItems.find { it.menu.menuId == menu.menuId }
                if (cartItem == null) {
                    // 다른 메뉴 CartItem 에 새로 추가
                    cartItemRepository.save(CartItem(cart = cart, menu = menu, quantity = quantity))
                } else {
                    // 같은 메뉴 수량 증가
                    cartItem.quantity += quantity
                }
            } else {
                // 장바구니에 담긴 메뉴ID와 새로 추가되는 메뉴ID가 다르다면 기존 장바구니 없애고 새로운 메뉴를 저장
                cartRepository.delete(cart)
                val newCart = cartRepository.save(Cart(member = member, store = menu.store))
                cartItemRepository.save(CartItem(cart = newCart, menu = menu, quantity = quantity))
            }
        }
    }


    // 장바구니 리스트
    fun getCartList(): List<CartDto> {
        val carts = cartRepository.findAll()
        return carts.map { CartDto.fromEntity(it) }
    }

    // 장바구니 특정 메뉴 삭제
    fun deleteCartMenu(memberId: Long, cartId: Long, menuId: Long) {
        val member = memberRepository.findById(memberId).orElseThrow { NotFoundException("사용자를 찾을 수 없습니다.") }
        val cart = cartRepository.findByCartIdAndMember(cartId, member)
            .orElseThrow { NotFoundException("장바구니가 비어있습니다.") }

        cartRepository.delete(cart)
    }

    fun findMemberAndMenu(memberId: Long, menuId: Long): Pair<Member, Menu> {
        val member = memberRepository.findById(memberId).orElseThrow { NotFoundException("사용자를 찾을 수 없습니다.") }
        val menu = menuRepository.findById(menuId).orElseThrow { NotFoundException("메뉴를 찾을 수 없습니다.") }
        return Pair(member, menu)
    }






}