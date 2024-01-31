package com.teamsparta.delivery_system.service

import com.teamsparta.delivery_system.domain.dto.CartDto
import com.teamsparta.delivery_system.domain.entity.Cart
import com.teamsparta.delivery_system.domain.entity.Member
import com.teamsparta.delivery_system.domain.entity.Menu
import com.teamsparta.delivery_system.domain.entity.Store
import com.teamsparta.delivery_system.domain.enums.MemberRole
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
    fun addCart(memberId: Long, request: CartAddRequest, menuId: Long): Cart {
        val (member, menu) = findMemberAndMenu(memberId, menuId)

        if(member != null) {
            val cart = Cart(
                menu = menu,
                quantity = request.quantity
            )
            return cartRepository.save(cart)
        } else {
            throw BadRequestException("메뉴를 추가할 수 없습니다")
        }
    }

    // 장바구니 리스트
    fun getCartList(): List<CartDto> {
        val carts = cartRepository.findAll()
        return CartDto.fromEntities(carts)
    }

    // 장바구니 메뉴 삭제
    fun deleteCartMenu(memberId: Long, cartId: Long, menuId: Long) {
        val (member, menu) = findMemberAndMenu(memberId, menuId)
        val cart = cartRepository.findById(cartId).orElseThrow { NotFoundException("장바구니가 비어있습니다.") }

        if(member != null && cart != null) {
            cartRepository.delete(cart)
        } else {
            throw BadRequestException("메뉴를 삭제할 수 없습니다.")
        }
    }

    fun findMemberAndMenu(memberId: Long, menuId: Long): Pair<Member, Menu> {
        val member = memberRepository.findById(memberId).orElseThrow { NotFoundException("사용자를 찾을 수 없습니다.") }
        val menu = menuRepository.findById(menuId).orElseThrow { NotFoundException("메뉴를 찾을 수 없습니다.") }
        return Pair(member, menu)
    }




}