package com.whatever.caramel.core.domain.usecase.couple

import com.whatever.caramel.core.domain.exception.AppExceptionCode
import com.whatever.caramel.core.domain.exception.CaramelException
import com.whatever.caramel.core.domain.repository.CoupleRepository

class ConnectCoupleUseCase(
    private val coupleRepository: CoupleRepository
) {
    suspend operator fun invoke(invitationCode: String) {
        if (invitationCode.isEmpty()) {
            throw CaramelException(
                code = AppExceptionCode.INVALID_PARAMS,
                message = "초대코드는 빈 값일 수 없습니다."
            )
        }
        coupleRepository.connectCouple(invitationCode = invitationCode)
    }
}