package com.whatever.caramel.core.datasource.remote.dto.app.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetUpdatePolicyResponse(
    @SerialName("forceUpdate") val forceUpdate: Boolean,
    @SerialName("updateUri") val updateUri: String?,
)
