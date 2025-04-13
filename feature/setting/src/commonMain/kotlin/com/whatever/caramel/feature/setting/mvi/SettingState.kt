package com.whatever.caramel.feature.setting.mvi

import com.whatever.caramel.core.domain.entity.User
import com.whatever.caramel.core.domain.vo.user.Gender
import com.whatever.caramel.core.viewmodel.UiState
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

data class SettingState(
    val isLoading: Boolean = true,
    val startDateTimeMillisecond: Long = 0L,
    val myInfo: CoupleUser = CoupleUser(),
    val partnerInfo: CoupleUser = CoupleUser(),
    val isShowProfileChangeBottomSheet: Boolean = false
) : UiState {
    val startDate: String
        get() = if (startDateTimeMillisecond == 0L) {
            "언제부터 사귀기 시작했나요?"
        } else {
            startDateTimeMillisecond.toFormattedDate()
        }
}

data class CoupleUser(
    val id: Long = 0L,
    val nickname: String = "",
    val birthDayTimeMillisecond: Long = 0L,
    val gender: Gender = Gender.IDLE
) {
    val birthDate: String
        get() = birthDayTimeMillisecond.toFormattedDate()

    companion object {
        fun toCoupleInfo(user: User) = CoupleUser(
            id = user.requireId,
            birthDayTimeMillisecond = user.requireProfile.birthdayMillisecond,
            nickname = user.requireProfile.nickName,
            gender = user.requireProfile.gender
        )
    }
}

// @RyuSw-cs 2025.04.08 FIXME : core-util에서 관리될 예정
fun Long.toFormattedDate(): String {
    val instant = Instant.fromEpochMilliseconds(this)
    val localDateTime = instant.toLocalDateTime(TimeZone.currentSystemDefault())

    val year = localDateTime.year.toString().padStart(4, '0')
    val month = localDateTime.month.ordinal.plus(1).toString().padStart(2, '0')
    val day = localDateTime.dayOfMonth.toString().padStart(2, '0')

    return "$year.$month.$day"
}