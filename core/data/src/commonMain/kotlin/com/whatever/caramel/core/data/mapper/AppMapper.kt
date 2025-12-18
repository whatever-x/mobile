package com.whatever.caramel.core.data.mapper

import com.whatever.caramel.core.domain.vo.app.CheckForceUpdateResult
import com.whatever.caramel.core.datasource.remote.dto.app.response.GetUpdatePolicyResponse

fun GetUpdatePolicyResponse.toCheckForceUpdateResult(): CheckForceUpdateResult =
    CheckForceUpdateResult(
        requireUpdate = this.forceUpdate,
        storeUri = this.updateUri,
    )
