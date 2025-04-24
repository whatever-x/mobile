package com.whatever.caramel.core.data.mapper

import com.whatever.caramel.core.domain.entity.Couple
import com.whatever.caramel.core.domain.entity.User
import com.whatever.caramel.core.domain.exception.CaramelException
import com.whatever.caramel.core.domain.exception.code.AppErrorCode
import com.whatever.caramel.core.domain.vo.couple.CoupleInvitationCode
import com.whatever.caramel.core.domain.vo.couple.CoupleRelationship
import com.whatever.caramel.core.domain.vo.couple.CoupleStatus
import com.whatever.caramel.core.domain.vo.user.Gender
import com.whatever.caramel.core.domain.vo.user.UserProfile
import com.whatever.caramel.core.remote.dto.couple.response.CoupleBasicResponse
import com.whatever.caramel.core.remote.dto.couple.response.CoupleDetailResponse
import com.whatever.caramel.core.remote.dto.couple.response.CoupleInvitationCodeResponse
import com.whatever.caramel.core.remote.dto.couple.response.CoupleUserInfoDto
import com.whatever.caramel.core.util.DateParser.toMillisecond
import kotlinx.datetime.Instant

fun CoupleInvitationCodeResponse.toCoupleInvitationCode() = CoupleInvitationCode(
    invitationCode = this.invitationCode,
    expirationMillisecond = Instant.parse(expirationDateTime).toEpochMilliseconds()
)

fun CoupleDetailResponse.toCoupleRelationship(): CoupleRelationship =
    CoupleRelationship(
        info = Couple(
            id = this.coupleId,
            startDateMillis = this.startDate?.toMillisecond() ?: 0L,
            sharedMessage = this.sharedMessage ?: "",
            status = CoupleStatus.valueOf(this.status.name)
        ),
        myInfo = this.myInfo.toUser(),
        partnerInfo = this.partnerInfo.toUser()
    )

fun CoupleUserInfoDto.toUser(): User =
    User(
        id = this.id,
        userProfile = UserProfile(
            nickName = this.nickname,
            birthdayMillisecond = this.birthDate.toMillisecond() ?: 0L,
            gender = Gender.valueOf(this.gender.name)
        )
    )

fun CoupleBasicResponse.toCouple(): Couple =
    Couple(
        id = this.coupleId,
        startDateMillis = this.startDate?.toMillisecond() ?: throw CaramelException(
            code = AppErrorCode.INVALID_PARAMS,
            message = "알 수 없는 오류입니다.",
            debugMessage = "날짜 형식 변환에 실패했습니다."
        ),
        sharedMessage = this.sharedMessage ?: throw CaramelException(
            code = AppErrorCode.INVALID_PARAMS,
            message = "알 수 없는 오류입니다.",
            debugMessage = "공유 메시지 형식 변환에 실패했습니다."
        ),
        status = CoupleStatus.valueOf(this.status.name)
    )
