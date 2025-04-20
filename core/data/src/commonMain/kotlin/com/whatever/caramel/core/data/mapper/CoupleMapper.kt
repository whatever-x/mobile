package com.whatever.caramel.core.data.mapper

import com.whatever.caramel.core.domain.entity.Couple
import com.whatever.caramel.core.domain.entity.CoupleInfo
import com.whatever.caramel.core.domain.entity.User
import com.whatever.caramel.core.domain.exception.CaramelException
import com.whatever.caramel.core.domain.exception.code.AppErrorCode
import com.whatever.caramel.core.domain.vo.couple.CoupleInvitationCode
import com.whatever.caramel.core.domain.vo.user.Gender
import com.whatever.caramel.core.domain.vo.user.UserProfile
import com.whatever.caramel.core.remote.dto.couple.CoupleConnectResponse
import com.whatever.caramel.core.remote.dto.couple.CoupleInfoResponse
import com.whatever.caramel.core.remote.dto.couple.CoupleInvitationCodeResponse
import com.whatever.caramel.core.remote.dto.couple.CoupleStartDateUpdateResponse
import com.whatever.caramel.core.util.DateParser.toMillisecond
import kotlinx.datetime.Instant

fun CoupleInvitationCodeResponse.toCoupleInvitationCode() = CoupleInvitationCode(
    invitationCode = this.invitationCode,
    expirationMillisecond = Instant.parse(expirationDateTime).toEpochMilliseconds()
)

fun CoupleConnectResponse.toCouple() = Couple(
    info = CoupleInfo(
        id = this.coupleId,
        startDateMillis = this.startDate.toMillisecond() ?: 0L,
        sharedMessage = this.sharedMessage,
    ),
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

fun CoupleInfoResponse.toCouple() = Couple(
    info = CoupleInfo(
        id = this.coupleId,
        startDateMillis = this.startDate?.toMillisecond() ?: 0L,
        sharedMessage = this.sharedMessage ?: "",
    ),
    myInfo = User(
        id = this.myInfo.id,
        userProfile = UserProfile(
            nickName = this.myInfo.nickname,
            birthdayMillisecond = this.myInfo.birthDate.toMillisecond() ?: 0L,
            gender = Gender.valueOf(this.myInfo.gender)
        )
    ),
    partnerInfo = User(
        id = this.partnerInfo.id,
        userProfile = UserProfile(
            nickName = this.partnerInfo.nickname,
            birthdayMillisecond = this.partnerInfo.birthDate.toMillisecond() ?: 0L,
            gender = Gender.valueOf(this.myInfo.gender)
        )
    )
)

fun CoupleStartDateUpdateResponse.toCoupleInfo() = CoupleInfo(
    id = this.coupleId,
    startDateMillis = this.startDate.toMillisecond() ?: throw CaramelException(
        code = AppErrorCode.INVALID_PARAMS,
        message = "알 수 없는 오류입니다.",
        debugMessage = "날짜 형식 변환에 실패했습니다."
    ),
    sharedMessage = this.sharedMessage ?: ""
)