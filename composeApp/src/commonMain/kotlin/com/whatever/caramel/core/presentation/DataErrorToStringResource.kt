package com.whatever.caramel.core.presentation

import app.composeapp.generated.resources.Res
import app.composeapp.generated.resources.title_test
import com.whatever.caramel.core.domain.DataError

fun DataError.toUiText(): UiText {
    // FIXME : 에러 메세지 확인
    val stringRes = when(this) {
        DataError.Local.DISK_FULL -> Res.string.title_test
        DataError.Local.UNKNOWN -> Res.string.title_test
        DataError.Remote.REQUEST_TIMEOUT -> Res.string.title_test
        DataError.Remote.TOO_MANY_REQUESTS -> Res.string.title_test
        DataError.Remote.NO_INTERNET -> Res.string.title_test
        DataError.Remote.SERVER -> Res.string.title_test
        DataError.Remote.SERIALIZATION -> Res.string.title_test
        DataError.Remote.UNKNOWN -> Res.string.title_test
    }
    return UiText.StringResourceId(stringRes)
}