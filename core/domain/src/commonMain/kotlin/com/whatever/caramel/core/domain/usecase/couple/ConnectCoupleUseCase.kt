package com.whatever.caramel.core.domain.usecase.couple

import com.whatever.caramel.core.domain.entity.user.UserStatus
import com.whatever.caramel.core.domain.exception.couple.ExpiredInvitationCodeException
import com.whatever.caramel.core.domain.exception.couple.InvalidInvitationCodeException
import com.whatever.caramel.core.domain.exception.user.InvalidUserStatusException
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
            throw InvalidInvitationCodeException(message = "Invitation code cannot be empty")
        }
        val currentMillisecond = Clock.System.now().toEpochMilliseconds()
        if (expirationMillisecond <= currentMillisecond) {
            throw ExpiredInvitationCodeException(message = "Invitation code has expired")
        }
        val userStatus = userRepository.getUserStatus()
        if (userStatus != UserStatus.SINGLE) {
            throw InvalidUserStatusException(message = "User is not single, cannot connect couple")
        }
        coupleRepository.connectCouple(invitationCode)
        userRepository.setUserStatus(UserStatus.COUPLED)
    }
}