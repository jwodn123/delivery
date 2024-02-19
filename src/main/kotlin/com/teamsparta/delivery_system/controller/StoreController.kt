package com.teamsparta.delivery_system.controller

import com.teamsparta.delivery_system.domain.dto.StoreDto
import com.teamsparta.delivery_system.exception.BadRequestException
import org.springframework.security.core.userdetails.User
import com.teamsparta.delivery_system.service.StoreService
import com.teamsparta.delivery_system.web.request.StoreCreateRequest
import com.teamsparta.delivery_system.web.request.StoreUpdateRequest
import com.teamsparta.delivery_system.web.response.ListResponse
import com.teamsparta.delivery_system.web.response.SingleResponse
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RequestMapping("/api/stores")
@RestController
class StoreController(
    private val storeservice: StoreService
) {

    //가게 등록
    @PostMapping
    fun storeRegistration(
        @AuthenticationPrincipal user: User,
        @Valid @RequestBody request: StoreCreateRequest
    ): ResponseEntity<SingleResponse<StoreDto>> {
        try {


            val store = storeservice.createStore(user.username.toLong(), request)
            return ResponseEntity
                .status(HttpStatus.OK)
                .body(SingleResponse.successOf("가게 등록에 성공했습니다!", StoreDto.fromEntity(store)))
        } catch (e: BadRequestException) {
            throw BadRequestException("가게 등록 실패!!")
        }
    }

    // 가게 전체 조회
    @GetMapping
    fun getStoreList(): ResponseEntity<ListResponse<StoreDto>> {
        val stores = storeservice.getAllStores()
        return ResponseEntity(ListResponse.successOf(stores), HttpStatus.OK)
    }

    // 특정 가게 조회
    @GetMapping("/{storeId}")
    fun findStore(@PathVariable storeId: Long): ResponseEntity<SingleResponse<StoreDto>> {
        val store = storeservice.getStoreById(storeId)
        return ResponseEntity(SingleResponse.successOf(store), HttpStatus.OK)
    }

    // 가게 수정
    @PutMapping("/{storeId}")
    fun updateStore(
        @AuthenticationPrincipal user: User,
        @PathVariable storeId: Long,
        @Valid @RequestBody request: StoreUpdateRequest
    ): ResponseEntity<SingleResponse<StoreDto>> {
        val store = storeservice.updateStore(user.username.toLong(), request, storeId)
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(SingleResponse.successOf("가게 정보 수정에 성공했습니다!", StoreDto.fromEntity(store)))
    }

    // 가게 삭제
    @DeleteMapping("/{storeId}")
    fun deleteStore(
        @AuthenticationPrincipal user: User,
        @PathVariable storeId: Long
    ): ResponseEntity<SingleResponse<String>> {
        storeservice.deleteStore(user.username.toLong(), storeId)
        return ResponseEntity(SingleResponse.success(), HttpStatus.OK)
    }

}