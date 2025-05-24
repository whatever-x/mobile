package com.whatever.caramel.core.domain.entity

import kotlinx.datetime.LocalDateTime

data class Todo(
    val id: Long,
    val startDate: LocalDateTime,
    val endDate: LocalDateTime,
    val title: String,
    val description: String?
) {
    val url: String?
        get() {
            if (description == null) return null
            return URL_PATTERN.findAll(description).map { it.value }.firstOrNull()
        }

    companion object {
        val URL_PATTERN = Regex(
            "(https?://|www\\.)[-a-zA-Z0-9@:%._+~#=]{1,256}\\.[a-zA-Z0-9()]{1,6}\\b([-a-zA-Z0-9()@:%_+.~#?&/=]*)"
        )
    }
}