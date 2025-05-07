package com.whatever.caramel.core.domain.usecase.couple

import com.whatever.caramel.core.domain.repository.CoupleRepository
import com.whatever.caramel.core.domain.repository.UserRepository

class ConnectCoupleUseCase(
    private val userRepository: UserRepository,
    private val coupleRepository: CoupleRepository,
) {

    suspend operator fun invoke(
        invitationCode: String,
    ) {
        val coupleRelationship = coupleRepository.connectCouple(invitationCode = invitationCode)
        val myInfo = coupleRelationship.myInfo

        userRepository.setUserStatus(myInfo.userStatus)
    }

}