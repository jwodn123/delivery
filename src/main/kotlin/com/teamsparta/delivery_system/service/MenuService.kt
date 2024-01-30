package com.teamsparta.delivery_system.service

import com.teamsparta.delivery_system.domain.dto.MenuDto
import com.teamsparta.delivery_system.domain.dto.StoreDto
import com.teamsparta.delivery_system.domain.entity.Member
import com.teamsparta.delivery_system.domain.entity.Menu
import com.teamsparta.delivery_system.domain.entity.Store
import com.teamsparta.delivery_system.domain.enums.MemberRole
import com.teamsparta.delivery_system.exception.BadRequestException
import com.teamsparta.delivery_system.exception.NotFoundException
import com.teamsparta.delivery_system.exception.ServerException
import com.teamsparta.delivery_system.repository.MemberRepository
import com.teamsparta.delivery_system.repository.MenuRespository
import com.teamsparta.delivery_system.repository.StoreRepository
import com.teamsparta.delivery_system.web.request.MenuCreateRequest
import com.teamsparta.delivery_system.web.request.MenuUpdateRequest
import jakarta.transaction.Transactional
import lombok.RequiredArgsConstructor
import org.springframework.stereotype.Service

@Service
class MenuService(
    private val menuRespository: MenuRespository,
    private val memberRepository: MemberRepository,
    private val storeRepository: StoreRepository
) {

    // 메뉴 등록
    @Transactional
    fun createMenu(memberId: Long, request: MenuCreateRequest, storeId: Long): Menu {

        val (member, store) = findMemberAndStore(memberId, storeId)

        if(member.role == MemberRole.OWNER && memberId == store?.member?.memberId) {
            val menu = Menu(
                store = store,
                category = request.category,
                name = request.name,
                explanation = request.explanation,
                price = request.price,
            )
            return menuRespository.save(menu)
        } else {
            throw BadRequestException("해당 가게 사장님만 메뉴를 등록 할 수 있습니다.")
        }
    }

    // 메뉴 전체 조회
//    fun getAllMenus(): List<MenuDto> {
//        val menus = menuRespository.findAll()
//        return MenuDto.fromEntities(menus)
//    }


    // 특정 메뉴 조회
    fun getMenuById(menuId: Long): MenuDto {
        val menu = menuRespository.findById(menuId).orElseThrow{ NotFoundException("해당 메뉴를 찾을 수 없습니다.") }
        return MenuDto.fromEntity(menu)
    }

    // 메뉴 수정
    @Transactional
    fun updateMenu(memberId: Long, request: MenuUpdateRequest, storeId: Long, menuId: Long): Menu {
        val (member, store, menu) = findMemberStoreAndMenu(memberId, storeId, menuId)

        if (member.role == MemberRole.OWNER && memberId == store?.member?.memberId) {
            menu.update(
                request.category,
                request.name,
                request.explanation,
                request.price
            )
        } else {
            throw BadRequestException("해당 가게 사장님만 수정할 수 있습니다.")
        }
        return menu
    }

    // 메뉴 삭제
    fun deleteMenu(memberId: Long, storeId: Long, menuId: Long) {
        val (member, store, menu) = findMemberStoreAndMenu(memberId, storeId, menuId)

        if(member.role == MemberRole.OWNER && memberId == store?.member?.memberId) {
            menuRespository.delete(menu)
        } else {
            throw BadRequestException("해당 가게 사장님만 삭제할 수 있습니다.")
        }
    }

    fun findMemberStoreAndMenu(memberId: Long, storeId: Long, menuId: Long): Triple<Member, Store, Menu> {
        val member = memberRepository.findById(memberId).orElseThrow { NotFoundException("사용자를 찾을 수 없습니다.") }
        val store = storeRepository.findById(storeId).orElseThrow { NotFoundException("수정할 가게를 찾을 수 없습니다.") }
        val menu = menuRespository.findById(menuId).orElseThrow { NotFoundException("수정할 메뉴를 찾을 수 없습니다.") }
        return Triple(member, store, menu)
    }

    fun findMemberAndStore(memberId: Long, storeId: Long): Pair<Member, Store> {
        val member = memberRepository.findById(memberId).orElseThrow { NotFoundException("사용자를 찾을 수 없습니다.") }
        val store = storeRepository.findById(storeId).orElseThrow { NotFoundException("해당 가게를 찾을 수 없습니다.") }
        return Pair(member, store)
    }
}