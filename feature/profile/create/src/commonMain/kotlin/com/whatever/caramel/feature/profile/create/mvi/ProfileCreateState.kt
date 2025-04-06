package com.whatever.caramel.feature.profile.create.mvi

import com.whatever.caramel.core.domain.vo.user.Gender
import com.whatever.caramel.core.viewmodel.UiState
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

data class ProfileCreateState(
    val nickname: String = "",
    val gender: Gender = Gender.IDLE,
    val birthday: Birthday = Birthday.currentDay(),
    val isServiceTermChecked: Boolean = false,
    val isPersonalInfoTermChecked: Boolean = false,
    val currentStep: ProfileCreateStep = ProfileCreateStep.NICKNAME,
) : UiState {

    val isNextButtonEnabled: Boolean
        get() = when (currentStep) {
            ProfileCreateStep.NICKNAME -> nickname.isNotEmpty()
            ProfileCreateStep.GENDER -> gender != Gender.IDLE
            ProfileCreateStep.BIRTHDAY -> true
            ProfileCreateStep.NEED_TERMS -> isServiceTermChecked && isPersonalInfoTermChecked
        }

    val currentIndex: Int
        get() = ProfileCreateStep.entries.indexOf(currentStep)

    val buttonText: String
        get() = when (currentStep) {
            ProfileCreateStep.NEED_TERMS -> "가입 완료!"
            else -> "다음"
        }

}

enum class ProfileCreateStep {
    NICKNAME,
    GENDER,
    BIRTHDAY,
    NEED_TERMS,
    ;
}

data class Birthday(
    val year: String = "",
    val month: String = "",
    val day: String = ""
) {
    fun toDateFormat() : String {
        fun toFormatNumber(number : String) = if(number.toInt() in 1..9){
            "0$number"
        } else {
            number
        }

        return "${year}-${toFormatNumber(month)}-${toFormatNumber(day)}"
    }

    companion object {
        fun currentDay(): Birthday {
            val currentDay =
                Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date

            return Birthday(
                year = currentDay.year.toString(),
                month = (currentDay.month.ordinal + 1).toString(),
                day = currentDay.dayOfMonth.toString()
            )
        }
    }
}
