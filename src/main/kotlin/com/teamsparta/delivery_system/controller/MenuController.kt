package com.teamsparta.delivery_system.controller

import com.teamsparta.delivery_system.domain.dto.MenuDto
import com.teamsparta.delivery_system.domain.entity.Member
import com.teamsparta.delivery_system.domain.entity.Store
import com.teamsparta.delivery_system.exception.BadRequestException
import com.teamsparta.delivery_system.service.MenuService
import com.teamsparta.delivery_system.web.request.MenuCreateRequest
import com.teamsparta.delivery_system.web.request.MenuUpdateRequest
import com.teamsparta.delivery_system.web.response.ListResponse
import com.teamsparta.delivery_system.web.response.SingleResponse
import jakarta.servlet.http.HttpServletRequest
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.User
import org.springframework.web.bind.annotation.*

@RestController
class MenuController(
    private val menuService: MenuService
) {

    // 메뉴 등록
    @PostMapping("/api/stores/{storeId}/menus")
    fun createMenu(
        @AuthenticationPrincipal user: User,
        @PathVariable storeId: Long,
        @Valid @RequestBody request: MenuCreateRequest
    ): ResponseEntity<SingleResponse<MenuDto>> {
        try {
            val menu = menuService.createMenu(user.username.toLong(), request, storeId)
            return ResponseEntity
                .status(HttpStatus.OK)
                .body(SingleResponse.successOf("메뉴 등록에 성공했습니다!", MenuDto.fromEntity(menu)))
        } catch (e: BadRequestException) {
            throw BadRequestException("메뉴 등록 실패!!")
        }
    }


    // 메뉴 전체 조회
//    @GetMapping
//    fun findAllMenu(): ResponseEntity<ListResponse<MenuDto>> {
//        val menus = menuService.getAllMenus()
//        return ResponseEntity(ListResponse.successOf(menus), HttpStatus.OK)
//    }

    // 특정 메뉴 조회
    @GetMapping("/api/stores/{storeId}/menus/{menuId}")
    fun findMenu(@PathVariable menuId: Long): ResponseEntity<SingleResponse<MenuDto>> {
        val menu = menuService.getMenuById(menuId)
        return ResponseEntity(SingleResponse.successOf(menu), HttpStatus.OK)
    }

    // 메뉴 수정
    @PutMapping("/api/stores/{storeId}/menus/{menuId}")
    fun updateMenu(
        @AuthenticationPrincipal user: User,
        @PathVariable storeId: Long,
        @PathVariable menuId: Long,
        @Valid @RequestBody request: MenuUpdateRequest
    ): ResponseEntity<SingleResponse<MenuDto>> {
        val menu = menuService.updateMenu(user.username.toLong(), request, storeId, menuId)
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(SingleResponse.successOf("메뉴 수정에 성공했습니다!", MenuDto.fromEntity(menu)))
    }


    // 메뉴 삭제
    @DeleteMapping("/api/stores/{storeId}/menus/{menuId}")
    fun deleteMenu(
        @AuthenticationPrincipal user: User,
        @PathVariable storeId: Long,
        @PathVariable menuId: Long,
    ): ResponseEntity<SingleResponse<String>> {
        menuService.deleteMenu(user.username.toLong(), storeId, menuId)
        return ResponseEntity(SingleResponse.success(), HttpStatus.OK)
    }

}