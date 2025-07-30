package com.whatever.caramel.core.domain.legacy.legacy_ui

import kotlinx.datetime.LocalDate

data class TodosOnDate(
    val date: LocalDate,
    val todos: List<Todo>,
)