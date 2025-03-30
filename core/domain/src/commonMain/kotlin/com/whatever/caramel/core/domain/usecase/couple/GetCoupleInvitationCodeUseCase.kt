package com.whatever.caramel.core.domain.usecase.couple

import com.whatever.caramel.core.domain.exception.CaramelException
import com.whatever.caramel.core.domain.exception.code.UserErrorCode
import com.whatever.caramel.core.domain.vo.user.UserStatus
import com.whatever.caramel.core.domain.vo.couple.CoupleInvitationCode
import com.whatever.caramel.core.domain.repository.CoupleRepository
import com.whatever.caramel.core.domain.repository.UserRepository

class GetCoupleInvitationCodeUseCase(
    private val userRepository: UserRepository,
    private val coupleRepository: CoupleRepository
) {
    suspend operator fun invoke(): CoupleInvitationCode {
        val currentUserStatus = userRepository.getUserStatus()
        if (currentUserStatus != UserStatus.SINGLE) {
            throw CaramelException(
                code = UserErrorCode.INVALID_USER_STATUS,
                message = "유저의 상태가 싱글이 아니므로 커플 초대코드를 생성할 수 없습니다.",
                debugMessage = "User is not single"
            )
        }
        return coupleRepository.getCoupleInvitationCode()
    }
}