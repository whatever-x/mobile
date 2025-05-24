package com.whatever.caramel.core.domain.vo.calendar

import com.whatever.caramel.core.domain.entity.Todo
import kotlinx.datetime.LocalDate

data class TodoOnDate(
    val date : LocalDate,
    val todos : List<Todo>
)