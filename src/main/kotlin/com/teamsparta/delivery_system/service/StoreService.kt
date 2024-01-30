package com.teamsparta.delivery_system.service

import com.teamsparta.delivery_system.domain.dto.StoreDto
import com.teamsparta.delivery_system.domain.entity.Member
import com.teamsparta.delivery_system.repository.StoreRepository
import com.teamsparta.delivery_system.domain.entity.Store
import com.teamsparta.delivery_system.domain.enums.MemberRole
import com.teamsparta.delivery_system.exception.BadRequestException
import com.teamsparta.delivery_system.exception.InvalidCredentialException
import com.teamsparta.delivery_system.exception.NotFoundException
import com.teamsparta.delivery_system.exception.ServerException
import com.teamsparta.delivery_system.repository.MemberRepository
import com.teamsparta.delivery_system.web.request.StoreCreateRequest
import com.teamsparta.delivery_system.web.request.StoreUpdateRequest
import jakarta.transaction.Transactional
import lombok.RequiredArgsConstructor
import org.springframework.security.core.userdetails.User
import org.springframework.stereotype.Service

@Service
class StoreService(
    private val storeRepository: StoreRepository,
    private val memberRepository: MemberRepository
) {

    // 가게 등록
    @Transactional
    fun createStore(memberId: Long, request: StoreCreateRequest): Store {
        val member = memberRepository.findById(memberId).orElseThrow { NotFoundException("사용자를 찾을 수 없습니다.") }

        if (member.role == MemberRole.OWNER) {
            val store = Store(
                member = member,
                name = request.name,
                address = request.address,
                content = request.content,
                phone = request.phone,
                minDeliveryPrice = request.minDeliveryPrice,
                deliveryTip = request.deliveryTip,
                minDeliveryTime = request.minDeliveryTime,
                maxDeliveryTime = request.maxDeliveryTime
            )
            return storeRepository.save(store)
        } else {
            throw BadRequestException("가게 등록 권한이 없습니다.")
        }
    }

    // 가게 전체 조회
    fun getAllStores(): List<StoreDto> {
        val stores = storeRepository.findAll()
        return StoreDto.fromEntities(stores)
    }

    // 특정 가게 조회
    fun getStoreById(storeId: Long): StoreDto {
        val store = storeRepository.findById(storeId).orElseThrow{NotFoundException("해당 가게를 찾을 수 없습니다.")}
        return StoreDto.fromEntity(store)
    }

    // 가게 수정
    @Transactional
    fun updateStore(memberId: Long, request: StoreUpdateRequest, storeId: Long): Store {
        val (member, store) = findMemberAndStore(memberId, storeId)

        if (member.role == MemberRole.OWNER && memberId == store?.member?.memberId) {
            store.update(
                request.name,
                request.address,
                request.content,
                request.phone,
                request.minDeliveryPrice,
                request.deliveryTip,
                request.minDeliveryTime,
                request.maxDeliveryTime
            )
        } else {
            throw BadRequestException("해당 가게 사장님만 수정할 수 있습니다.")
        }

        return store
    }

    // 가게 삭제
    fun deleteStore(memberId: Long, storeId: Long) {
        val (member, store) = findMemberAndStore(memberId, storeId)

        if(member.role == MemberRole.OWNER && memberId == store?.member?.memberId) {
            storeRepository.delete(store)
        } else {
            throw BadRequestException("해당 가게 사장님만 삭제할 수 있습니다.")
        }
    }

    fun findMemberAndStore(memberId: Long, storeId: Long): Pair<Member, Store> {
        val member = memberRepository.findById(memberId).orElseThrow { NotFoundException("사용자를 찾을 수 없습니다.") }
        val store = storeRepository.findById(storeId).orElseThrow { NotFoundException("해당 가게를 찾을 수 없습니다.") }
        return Pair(member, store)
    }


}