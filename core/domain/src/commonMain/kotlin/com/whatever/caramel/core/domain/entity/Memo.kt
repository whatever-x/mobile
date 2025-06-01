package com.whatever.caramel.core.domain.entity

import kotlinx.datetime.LocalDate

data class Memo(
    val id: Long,
    val title: String,
    val description: String,
    val isCompleted: Boolean,
    val tagList: List<Tag>,
    val createdAt : LocalDate
)
