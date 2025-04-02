package com.whatever.caramel.core.data.mapper

import com.whatever.caramel.core.domain.entity.Couple
import com.whatever.caramel.core.domain.entity.User
import com.whatever.caramel.core.domain.vo.couple.CoupleInvitationCode
import com.whatever.caramel.core.domain.vo.user.Gender
import com.whatever.caramel.core.domain.vo.user.UserProfile
import com.whatever.caramel.core.remote.dto.couple.CoupleConnectResponse
import com.whatever.caramel.core.remote.dto.couple.CoupleInvitationCodeResponse
import kotlinx.datetime.Instant

fun CoupleInvitationCodeResponse.toCoupleInvitationCode() = CoupleInvitationCode(
    invitationCode = this.invitationCode,
    expirationMillisecond = Instant.parse(expirationDateTime).toEpochMilliseconds()
)

fun CoupleConnectResponse.toCouple() = Couple(
    id = this.coupleId,
    startDateMillis = Instant.parse(this.startDate).toEpochMilliseconds(),
    sharedMessage = this.sharedMessage,
    myInfo = User(
        id = this.myInfo.id,
        userProfile = UserProfile(
            nickName = this.myInfo.nickname,
            birthdayMillisecond = Instant.parse(this.myInfo.birthDate).toEpochMilliseconds(),
            gender = Gender.IDLE
        )
    ),
    partnerInfo = User(
        id = this.partnerInfo.id,
        userProfile = UserProfile(
            nickName = this.partnerInfo.nickname,
            birthdayMillisecond = Instant.parse(this.partnerInfo.birthDate).toEpochMilliseconds(),
            gender = Gender.IDLE
        )
    )
)