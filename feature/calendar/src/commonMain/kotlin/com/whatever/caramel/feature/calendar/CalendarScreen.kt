package com.whatever.caramel.feature.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetValue
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.whatever.caramel.core.designsystem.components.CaramelTopBar
import com.whatever.caramel.core.designsystem.themes.CaramelTheme
import com.whatever.caramel.feature.calendar.component.CalendarDatePicker
import com.whatever.caramel.feature.calendar.component.bottomSheet.CaramelBottomSheetHandle
import com.whatever.caramel.feature.calendar.component.CurrentDateMenu
import com.whatever.caramel.feature.calendar.component.bottomSheet.BottomSheetTodoItem
import com.whatever.caramel.feature.calendar.component.bottomSheet.BottomSheetTodoListHeader
import com.whatever.caramel.feature.calendar.component.bottomSheet.DefaultBottomSheetTodoItem
import com.whatever.caramel.feature.calendar.component.calendar.CaramelCalendar
import com.whatever.caramel.feature.calendar.mvi.BottomSheetState
import com.whatever.caramel.feature.calendar.mvi.CalendarIntent
import com.whatever.caramel.feature.calendar.mvi.CalendarState
import com.whatever.caramel.feature.calendar.mvi.Schedule
import io.github.aakira.napier.Napier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun CalendarScreen(
    state: CalendarState,
    onIntent: (CalendarIntent) -> Unit
) {
    val bottomSheetState = rememberStandardBottomSheetState()
    val bottomSheetScaffoldState =
        rememberBottomSheetScaffoldState(bottomSheetState = bottomSheetState)

    LaunchedEffect(bottomSheetState.currentValue) {
        val sheetState = when (bottomSheetState.currentValue) {
            SheetValue.Hidden -> BottomSheetState.HIDDEN
            SheetValue.Expanded -> BottomSheetState.EXPANDED
            SheetValue.PartiallyExpanded -> BottomSheetState.PARTIALLY_EXPANDED
        }
        onIntent(CalendarIntent.ToggleCalendarBottomSheet(sheetState))
    }

    BottomSheetScaffold(
        scaffoldState = bottomSheetScaffoldState,
        sheetPeekHeight = 62.dp,
        sheetShape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
        sheetContainerColor = CaramelTheme.color.background.primary,
        sheetContentColor = CaramelTheme.color.background.tertiary,
        sheetDragHandle = {
            CaramelBottomSheetHandle(
                bottomSheetState = state.bottomSheetState
            )
        },
        topBar = {
            Column(modifier = Modifier.fillMaxWidth()) {
                CaramelTopBar(
                    modifier = Modifier.background(color = CaramelTheme.color.background.primary),
                    leadingContent = {
                        CurrentDateMenu(
                            year = state.year,
                            month = state.month,
                            isShowDropMenu = state.isShownDateSelectDropDown,
                            onShowDropMenu = { onIntent(CalendarIntent.OpenCalendarDatePicker) }
                        )
                    }
                )
                CalendarDatePicker(
                    year = state.year,
                    month = state.month,
                    isShowDropMenu = state.isShownDateSelectDropDown,
                    onDismiss = { year, monthNumber ->
                        onIntent(
                            CalendarIntent.DismissCalendarDatePicker(
                                year = year,
                                monthNumber = monthNumber
                            )
                        )
                    }
                )
            }
        },
        sheetContent = {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = CaramelTheme.color.background.tertiary)
                    .padding(
                        top = CaramelTheme.spacing.xs,
                        bottom = 36.dp,
                        start = 20.dp,
                        end = 20.dp
                    )
            ) {
                state.schedulesByDate.forEach { schedule ->
                    Napier.e { "update: $schedule" }
                    Napier.e { "today: ${state.today}" }
                    item {
                        BottomSheetTodoListHeader(
                            modifier = Modifier,
                            date = schedule.date,
                            onClickAddSchedule = {
                                onIntent(
                                    CalendarIntent.ClickAddScheduleButton(
                                        it.toString()
                                    )
                                )
                            },
                            isToday = schedule.date == state.today,
                            isEmpty = schedule.listSize == 0,
                            holidays = if (schedule is Schedule.Holidays) schedule.holidays else null
                        )
                    }

                    if (schedule is Schedule.Todos) {
                        items(items = schedule.todos) { todo ->
                            BottomSheetTodoItem(
                                id = todo.id,
                                title = todo.title,
                                description = todo.description,
                                url = todo.url,
                                onClickUrl = { onIntent(CalendarIntent.ClickTodoUrl(it)) },
                                onClickTodo = { onIntent(CalendarIntent.ClickTodoItem(it)) }
                            ) {
                                DefaultBottomSheetTodoItem()
                            }
                        }
                    }
                }
            }
        }
    ) {
        BoxWithConstraints {
            CaramelCalendar(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(height = maxHeight - 62.dp)
                    .background(color = CaramelTheme.color.background.primary),
                year = state.year,
                month = state.month,
                schedules = state.schedulesByPriority
            )
        }
    }
}