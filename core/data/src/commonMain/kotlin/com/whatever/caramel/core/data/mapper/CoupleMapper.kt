package com.whatever.caramel.core.data.mapper

import com.whatever.caramel.core.domain.entity.Couple
import com.whatever.caramel.core.domain.entity.User
import com.whatever.caramel.core.domain.vo.couple.Anniversary
import com.whatever.caramel.core.domain.vo.couple.AnniversaryType
import com.whatever.caramel.core.domain.vo.couple.CoupleInvitationCode
import com.whatever.caramel.core.domain.vo.couple.CoupleRelationship
import com.whatever.caramel.core.domain.vo.user.Gender
import com.whatever.caramel.core.domain.vo.user.UserProfile
import com.whatever.caramel.core.domain.vo.user.UserStatus
import com.whatever.caramel.core.remote.dto.couple.CoupleUserInfoDto
import com.whatever.caramel.core.remote.dto.couple.response.CoupleAnniversaryResponse
import com.whatever.caramel.core.remote.dto.couple.response.CoupleBasicResponse
import com.whatever.caramel.core.remote.dto.couple.response.CoupleDetailResponse
import com.whatever.caramel.core.remote.dto.couple.response.CoupleInvitationCodeResponse
import kotlinx.datetime.LocalDate

fun CoupleInvitationCodeResponse.toCoupleInvitationCode() =
    CoupleInvitationCode(
        invitationCode = this.invitationCode,
        expirationDateTime = expirationDateTime,
    )

fun CoupleDetailResponse.toCoupleRelationship(): CoupleRelationship =
    CoupleRelationship(
        info =
            Couple(
                id = this.coupleId,
                startDate = this.startDate?.replace("-", ".") ?: "",
                sharedMessage = this.sharedMessage ?: "",
            ),
        myInfo = this.myInfo.toUser(),
        partnerInfo = this.partnerInfo.toUser(),
    )

fun CoupleUserInfoDto.toUser(): User =
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

fun CoupleBasicResponse.toCouple(): Couple =
    Couple(
        id = this.coupleId,
        startDate = this.startDate?.replace("-", ".") ?: "",
        sharedMessage = this.sharedMessage ?: "",
    )

// @@@ 리팩토링 필요. Anniversary는 각각 타입이 정해져 있음. N주년, N일째, 생일 API 내려올 때 ID값을 받아 하나의 리스트로 내려오는게 기념일 리스트가 필요로 할 때 쓰기 더 쉬울듯함
fun CoupleAnniversaryResponse.toAnniversaries(): List<Anniversary> {
    val hundredDayAnniversaries =
        this.hundredDayAnniversaries.map { coupleAnniversary ->
            Anniversary(
                date = LocalDate.parse(coupleAnniversary.date),
                type = AnniversaryType.valueOf(coupleAnniversary.type),
                label = coupleAnniversary.label,
            )
        }

    val yearlyAnniversaries =
        this.yearlyAnniversaries.map {
            Anniversary(
                date = LocalDate.parse(it.date),
                type = AnniversaryType.valueOf(it.type),
                label = it.label,
            )
        }

    val myBirthdayAnniversaries =
        this.myBirthDates.map {
            Anniversary(
                date = LocalDate.parse(it.date),
                type = AnniversaryType.valueOf(it.type),
                label = it.label,
            )
        }

    val partnerBirthdayAnniversaries =
        this.partnerBirthDates.map {
            Anniversary(
                date = LocalDate.parse(it.date),
                type = AnniversaryType.valueOf(it.type),
                label = it.label,
            )
        }
    return hundredDayAnniversaries + yearlyAnniversaries + myBirthdayAnniversaries + partnerBirthdayAnniversaries
}
