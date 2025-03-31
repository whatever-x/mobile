package com.whatever.caramel.core.domain.usecase.couple

import com.whatever.caramel.core.domain.exception.CaramelException
import com.whatever.caramel.core.domain.exception.code.CoupleErrorCode
import com.whatever.caramel.core.domain.exception.code.UserErrorCode
import com.whatever.caramel.core.domain.vo.user.UserStatus
import com.whatever.caramel.core.domain.repository.CoupleRepository
import com.whatever.caramel.core.domain.repository.UserRepository
import kotlinx.datetime.Clock

class ConnectCoupleUseCase(
    private val userRepository: UserRepository,
    private val coupleRepository: CoupleRepository
) {
    suspend operator fun invoke(
        invitationCode: String,
        expirationMillisecond: Long
    ) {
        if (invitationCode.isEmpty()) {
            throw CaramelException(
                code = CoupleErrorCode.INVALID_INVITATION_CODE,
                message = "유효하지 않은 커플 초대 코드입니다.",
                debugMessage = "Invitation code cannot be empty"
            )
        }
        val currentMillisecond = Clock.System.now().toEpochMilliseconds()
        if (expirationMillisecond <= currentMillisecond) {
            throw CaramelException(
                code = CoupleErrorCode.EXPIRED_INVITATION_CODE,
                message = "만료된 커플 초대 코드입니다.",
                debugMessage = "Invitation code has expired",
            )
        }
        val userStatus = userRepository.getUserStatus()
        if (userStatus != UserStatus.SINGLE) {
            throw CaramelException(
                code = UserErrorCode.INVALID_USER_STATUS,
                message = "유효하지 않은 사용자 상태입니다. 커플 연결을 위해 사용자는 싱글 상태여야 합니다.",
                debugMessage = "User is not single, cannot connect couple"
            )
        }
        coupleRepository.connectCouple(invitationCode)
        userRepository.setUserStatus(UserStatus.COUPLED)
    }
}