package com.whatever.caramel.core.domain.usecase.couple

import com.whatever.caramel.core.domain.entity.user.UserStatus
import com.whatever.caramel.core.domain.exception.user.InvalidUserStatusException
import com.whatever.caramel.core.domain.model.couple.CoupleInvitationCode
import com.whatever.caramel.core.domain.repository.CoupleRepository
import com.whatever.caramel.core.domain.repository.UserRepository

class GetCoupleInvitationCodeUseCase(
    private val userRepository: UserRepository,
    private val coupleRepository: CoupleRepository
) {
    suspend operator fun invoke(): CoupleInvitationCode {
        val currentUserStatus = userRepository.getUserStatus()
        if (currentUserStatus != UserStatus.SINGLE) {
            throw InvalidUserStatusException(message = "User is not single")
        }
        return coupleRepository.getCoupleInvitationCode()
    }
}