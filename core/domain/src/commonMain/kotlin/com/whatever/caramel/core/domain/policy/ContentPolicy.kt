package com.whatever.caramel.core.domain.policy

data object ContentPolicy {
    const val MAX_TITLE_LENGTH = 30
    const val MAX_BODY_LENGTH = 5000

    val URL_PATTERN =
        Regex(
            "(https?://|www\\.)[-a-zA-Z0-9@:%._+~#=]{1,256}\\.[a-zA-Z0-9()]{1,6}\\b([-a-zA-Z0-9()@:%_+.~#?&/=]*)",
        )
}