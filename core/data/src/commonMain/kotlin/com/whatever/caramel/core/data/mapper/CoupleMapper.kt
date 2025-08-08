package com.whatever.caramel.core.data.mapper

import com.whatever.caramel.core.domain.entity.Couple
import com.whatever.caramel.core.domain.entity.User
import com.whatever.caramel.core.domain.vo.couple.CoupleInvitationCode
import com.whatever.caramel.core.domain.vo.couple.CoupleRelationship
import com.whatever.caramel.core.domain.vo.couple.CoupleStatus
import com.whatever.caramel.core.domain.vo.user.Gender
import com.whatever.caramel.core.domain.vo.user.UserProfile
import com.whatever.caramel.core.domain.vo.user.UserStatus
import com.whatever.caramel.core.remote.dto.couple.CoupleUserInfoDto
import com.whatever.caramel.core.remote.dto.couple.response.CoupleBasicResponse
import com.whatever.caramel.core.remote.dto.couple.response.CoupleDetailResponse
import com.whatever.caramel.core.remote.dto.couple.response.CoupleInvitationCodeResponse

internal fun CoupleInvitationCodeResponse.toCoupleInvitationCode() =
    CoupleInvitationCode(
        invitationCode = this.invitationCode,
        expirationDateTime = expirationDateTime,
    )

internal fun CoupleDetailResponse.toCoupleRelationship(): CoupleRelationship =
    CoupleRelationship(
        info =
            Couple(
                id = this.coupleId,
                startDate = this.startDate?.replace("-", ".") ?: "",
                sharedMessage = this.sharedMessage ?: "",
                status = CoupleStatus.valueOf(this.status.name),
            ),
        myInfo = this.myInfo.toUser(),
        partnerInfo = this.partnerInfo.toUser(),
    )

internal fun CoupleUserInfoDto.toUser(): User =
    User(
        id = this.id,
        userStatus = UserStatus.valueOf(this.userStatus.name),
        userProfile =
            UserProfile(
                nickName = this.nickname,
                birthday = this.birthDate.replace("-", "."),
                gender = Gender.valueOf(this.gender.name),
            ),
    )

internal fun CoupleBasicResponse.toCouple(): Couple =
    Couple(
        id = this.coupleId,
        startDate = this.startDate?.replace("-", ".") ?: "",
        sharedMessage = this.sharedMessage ?: "",
        status = CoupleStatus.valueOf(this.status.name),
    )
