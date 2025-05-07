package com.whatever.caramel.core.domain.vo.calendar

import com.whatever.caramel.core.domain.entity.Todo
import kotlinx.datetime.LocalDateTime

data class TodoList(
    val dateTime : LocalDateTime,
    val todos : List<Todo>
)