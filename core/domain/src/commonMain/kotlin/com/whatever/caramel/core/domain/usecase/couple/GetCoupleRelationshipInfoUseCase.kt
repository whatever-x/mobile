package com.whatever.caramel.core.domain.usecase.couple

import com.whatever.caramel.core.domain.repository.CoupleRepository
import com.whatever.caramel.core.domain.vo.couple.CoupleRelationship

class GetCoupleRelationshipInfoUseCase(
    private val coupleRepository: CoupleRepository,
) {
    suspend operator fun invoke(): CoupleRelationship {
        val coupleId =
            if (coupleRepository.readCoupleId() == 0L) { // @ham2174 TODO : 커플 아이디 인메모리 저장 형식으로 변경
                val coupleInfo = coupleRepository.getCoupleInfo() // 커플 정보 획득
                coupleRepository.setCoupleId(coupleId = coupleInfo.id) // 커플 아이디 저장
                coupleInfo.id
            } else {
                coupleRepository.readCoupleId()
            }

        return coupleRepository.getCoupleRelationshipInfo(coupleId = coupleId)
    }
}
