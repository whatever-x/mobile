package com.whatever.caramel.core.domain.vo.calendar

import com.whatever.caramel.core.domain.entity.Todo
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime

data class TodoList(
    val date : LocalDate,
    val todos : List<Todo>
)