package com.whatever.caramel.core.data.mapper

import com.whatever.caramel.core.domain.entity.Couple
import com.whatever.caramel.core.domain.entity.User
import com.whatever.caramel.core.domain.vo.couple.Anniversary
import com.whatever.caramel.core.domain.vo.couple.AnniversaryType
import com.whatever.caramel.core.domain.vo.couple.CoupleInvitationCode
import com.whatever.caramel.core.domain.vo.couple.CoupleRelationship
import com.whatever.caramel.core.domain.vo.couple.CoupleStatus
import com.whatever.caramel.core.domain.vo.user.Gender
import com.whatever.caramel.core.domain.vo.user.UserProfile
import com.whatever.caramel.core.domain.vo.user.UserStatus
import com.whatever.caramel.core.remote.dto.couple.CoupleUserInfoDto
import com.whatever.caramel.core.remote.dto.couple.response.CoupleAnniversaryResponse
import com.whatever.caramel.core.remote.dto.couple.response.CoupleBasicResponse
import com.whatever.caramel.core.remote.dto.couple.response.CoupleDetailResponse
import com.whatever.caramel.core.remote.dto.couple.response.CoupleInvitationCodeResponse
import com.whatever.caramel.core.util.DateFormatter
import kotlinx.datetime.LocalDate

fun CoupleInvitationCodeResponse.toCoupleInvitationCode() = CoupleInvitationCode(
    invitationCode = this.invitationCode,
    expirationDateTime = expirationDateTime
)

fun CoupleDetailResponse.toCoupleRelationship(): CoupleRelationship =
    CoupleRelationship(
        info = Couple(
            id = this.coupleId,
            startDate = this.startDate?.replace("-", ".") ?: "",
            sharedMessage = this.sharedMessage ?: "",
            status = CoupleStatus.valueOf(this.status.name)
        ),
        myInfo = this.myInfo.toUser(),
        partnerInfo = this.partnerInfo.toUser()
    )

fun CoupleUserInfoDto.toUser(): User =
    User(
        id = this.id,
        userStatus = UserStatus.valueOf(this.userStatus.name),
        userProfile = UserProfile(
            nickName = this.nickname,
            birthday = this.birthDate.replace("-", "."),
            gender = Gender.valueOf(this.gender.name)
        )
    )

fun CoupleBasicResponse.toCouple(): Couple =
    Couple(
        id = this.coupleId,
        startDate = this.startDate?.replace("-", ".") ?: "",
        sharedMessage = this.sharedMessage ?: "",
        status = CoupleStatus.valueOf(this.status.name)
    )

fun CoupleAnniversaryResponse.toAnniversary(): List<Anniversary> {
    val hundredDayAnniversaries = this.hundredDayAnniversaries.map {
        Anniversary(
            date = LocalDate.parse(it.date),
            type = AnniversaryType.valueOf(it.type),
            label = it.label,
            isAdjustedForNonLeapYear = it.isAdjustedForNonLeapYear
        )
    }

    val yearlyAnniversaries = this.yearlyAnniversaries.map {
        Anniversary(
            date = LocalDate.parse(it.date),
            type = AnniversaryType.valueOf(it.type),
            label = it.label,
            isAdjustedForNonLeapYear = it.isAdjustedForNonLeapYear
        )
    }

    val myBirthdayAnniversaries = this.myBirthDates.map {
        Anniversary(
            date = LocalDate.parse(it.date),
            type = AnniversaryType.valueOf(it.type),
            label = it.label,
            isAdjustedForNonLeapYear = it.isAdjustedForNonLeapYear
        )
    }

    val partnerBirthdayAnniversaries = this.partnerBirthDates.map {
        Anniversary(
            date = LocalDate.parse(it.date),
            type = AnniversaryType.valueOf(it.type),
            label = it.label,
            isAdjustedForNonLeapYear = it.isAdjustedForNonLeapYear
        )
    }
    return hundredDayAnniversaries + yearlyAnniversaries + myBirthdayAnniversaries + partnerBirthdayAnniversaries
}