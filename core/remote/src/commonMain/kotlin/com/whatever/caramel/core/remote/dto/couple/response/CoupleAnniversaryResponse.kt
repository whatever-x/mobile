package com.whatever.caramel.core.remote.dto.couple.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CoupleAnniversaryResponse(
    @SerialName("coupleId") val coupleId: Long,
    @SerialName("startDate") val startDate: String?,
    @SerialName("sharedMessage") val sharedMessage: String?,
    @SerialName("hundredDayAnniversaries") val hundredDayAnniversaries: List<CoupleAnniversary>,
    @SerialName("yearlyAnniversaries") val yearlyAnniversaries: List<CoupleAnniversary>,
    @SerialName("myBirthDates") val myBirthDates: List<CoupleAnniversary>,
    @SerialName("partnerBirthDates") val partnerBirthDates: List<CoupleAnniversary>,
)

@Serializable
data class CoupleAnniversary(
    @SerialName("type") val type: String,
    @SerialName("date") val date: String,
    @SerialName("label") val label: String,
    @SerialName("isAdjustedForNonLeapYear") val isAdjustedForNonLeapYear: Boolean
)