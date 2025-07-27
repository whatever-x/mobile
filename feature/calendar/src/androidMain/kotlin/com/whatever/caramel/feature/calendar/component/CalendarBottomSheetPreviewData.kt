package com.whatever.caramel.feature.calendar.component

import com.whatever.caramel.core.domain.vo.content.ContentAssignee
import com.whatever.caramel.feature.calendar.component.bottomSheet.CaramelDefaultBottomTodoScope
import org.jetbrains.compose.ui.tooling.preview.PreviewParameterProvider

internal class CalendarBottomSheetPreviewData : PreviewParameterProvider<CaramelDefaultBottomTodoScope> {
    private var idCounter: Long = 1

    override val values: Sequence<CaramelDefaultBottomTodoScope>
        get() =
            sequenceOf(
                CaramelDefaultBottomTodoScope(
                    id = idCounter++,
                    title = "일정 제목1",
                    description = "일정 내용1",
                    url = null,
                    assignee = ContentAssignee.US,
                    onClickUrl = {},
                    onClickTodo = {},
                ),
                CaramelDefaultBottomTodoScope(
                    id = idCounter++,
                    title = "30글자넘어가는건지30글자넘어가는건지30글자넘어가는건지",
                    description = "내용이4줄넘어가는건지내용이4줄넘어가는건지내용이4줄넘어가는건지내용이4줄넘어가는건지내용이4줄넘어가는건지내용이4줄넘어가는건지",
                    url = null,
                    assignee = ContentAssignee.ME,
                    onClickUrl = {},
                    onClickTodo = {},
                ),
                CaramelDefaultBottomTodoScope(
                    id = idCounter++,
                    title = "URL이 잘 나오는지",
                    description = "일정일정",
                    url = "https://www.naver.com",
                    assignee = ContentAssignee.PARTNER,
                    onClickUrl = {},
                    onClickTodo = {},
                ),
                CaramelDefaultBottomTodoScope(
                    id = idCounter++,
                    title = "제목만 있음",
                    description = "",
                    url = null,
                    assignee = ContentAssignee.PARTNER,
                    onClickUrl = {},
                    onClickTodo = {},
                ),
                CaramelDefaultBottomTodoScope(
                    id = idCounter++,
                    title = "",
                    description = "내용만 있음",
                    url = null,
                    assignee = ContentAssignee.PARTNER,
                    onClickUrl = {},
                    onClickTodo = {},
                ),
            )
}
