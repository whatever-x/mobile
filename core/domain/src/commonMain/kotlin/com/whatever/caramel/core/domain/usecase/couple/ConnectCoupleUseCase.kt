package com.whatever.caramel.core.domain.usecase.couple

import com.whatever.caramel.core.domain.repository.CoupleRepository
import com.whatever.caramel.core.domain.repository.UserRepository
import com.whatever.caramel.core.domain.vo.user.UserStatus

class ConnectCoupleUseCase(
    private val userRepository: UserRepository,
    private val coupleRepository: CoupleRepository
) {
    suspend operator fun invoke(
        invitationCode: String
    ) {
        coupleRepository.connectCouple(invitationCode)
        userRepository.setUserStatus(UserStatus.COUPLED)
    }
}