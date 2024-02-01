package com.teamsparta.delivery_system.service

import com.teamsparta.delivery_system.domain.dto.CartDto
import com.teamsparta.delivery_system.domain.entity.Cart
import com.teamsparta.delivery_system.domain.entity.Member
import com.teamsparta.delivery_system.domain.entity.Menu
import com.teamsparta.delivery_system.exception.BadRequestException
import com.teamsparta.delivery_system.exception.NotFoundException
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
    private val menuRepository: MenuRespository
) {

    // 장바구니에 메뉴 담기
    @Transactional
    fun addCart(memberId: Long, request: CartAddRequest): Cart {
        val (member, menu) = findMemberAndMenu(memberId, request.menuId)
        val cart = cartRepository.findByMember(member)

        if (cart != null) {
            if (cart.menu.store.storeId != menu.store.storeId) {
                throw BadRequestException("장바구니에는 같은 가게 메뉴만 담을 수 있습니다.")
            }
            cart.addQuantity(request.quantity)
            return cart
        }

        return cartRepository.save(Cart(
            member = member,
            menu = menu,
            quantity = request.quantity
        ))
    }


    // 장바구니 리스트
    fun getCartList(): List<CartDto> {
        val carts = cartRepository.findAll()
        return CartDto.fromEntities(carts)
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