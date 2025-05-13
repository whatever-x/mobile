package com.whatever.caramel.feature.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.graphics.Color
import com.whatever.caramel.core.designsystem.components.CaramelTopBar
import com.whatever.caramel.core.designsystem.themes.CaramelTheme
import com.whatever.caramel.feature.calendar.component.CalendarDatePicker
import com.whatever.caramel.feature.calendar.component.CurrentDateMenu
import com.whatever.caramel.feature.calendar.component.bottomSheet.BottomSheetTodoItem
import com.whatever.caramel.feature.calendar.component.bottomSheet.BottomSheetTodoListHeader
import com.whatever.caramel.feature.calendar.component.bottomSheet.CaramelBottomSheetHandle
import com.whatever.caramel.feature.calendar.component.bottomSheet.DefaultBottomSheetTodoItem
import com.whatever.caramel.feature.calendar.component.calendar.CalendarDayOfWeek
import com.whatever.caramel.feature.calendar.component.calendar.CaramelCalendar
import com.whatever.caramel.feature.calendar.dimension.CalendarDimension
import com.whatever.caramel.feature.calendar.mvi.BottomSheetState
import com.whatever.caramel.feature.calendar.mvi.CalendarIntent
import com.whatever.caramel.feature.calendar.mvi.CalendarState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun CalendarScreen(
    state: CalendarState,
    onIntent: (CalendarIntent) -> Unit
) {
    val bottomSheetState = rememberStandardBottomSheetState()
    val bottomSheetScaffoldState =
        rememberBottomSheetScaffoldState(bottomSheetState = bottomSheetState)

    LaunchedEffect(state.bottomSheetState) {
        val targetState = when (state.bottomSheetState) {
            BottomSheetState.HIDDEN -> SheetValue.Hidden
            BottomSheetState.EXPANDED -> SheetValue.Expanded
            BottomSheetState.PARTIALLY_EXPANDED -> SheetValue.PartiallyExpanded
        }

        if (bottomSheetState.currentValue != targetState) {
            when (targetState) {
                SheetValue.Hidden -> bottomSheetState.hide()
                SheetValue.Expanded -> bottomSheetState.expand()
                SheetValue.PartiallyExpanded -> bottomSheetState.partialExpand()
            }
        }
    }

    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        val totalHeight = maxHeight - CalendarDimension.sheetPeekHeight

        BottomSheetScaffold(
            scaffoldState = bottomSheetScaffoldState,
            sheetPeekHeight = CalendarDimension.sheetPeekHeight,
            sheetShape = RoundedCornerShape(
                topStart = CalendarDimension.sheetTopStartCornerRadius,
                topEnd = CalendarDimension.sheetTopEndCornerRadius
            ),
            sheetContainerColor = CaramelTheme.color.background.primary,
            sheetContentColor = CaramelTheme.color.background.tertiary,
            sheetDragHandle = {
                CaramelBottomSheetHandle(
                    bottomSheetState = state.bottomSheetState
                )
            },
            topBar = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = Color.Transparent)
                ) {
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
                val availableHeight =
                    totalHeight - CalendarDimension.datePickerHeight - CalendarDimension.dayOfWeekHeight + CalendarDimension.sheetPartiallyExpandedTextHeight
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = CaramelTheme.color.background.tertiary)
                        .padding(
                            top = CaramelTheme.spacing.xs,
                            bottom = CaramelTheme.spacing.l,
                            start = CaramelTheme.spacing.xl,
                            end = CaramelTheme.spacing.xl
                        )
                        .height(availableHeight)
                ) {
                    state.schedules.forEach { schedule ->
                        item {
                            BottomSheetTodoListHeader(
                                date = schedule.date,
                                onClickAddSchedule = {
                                    onIntent(
                                        CalendarIntent.ClickAddScheduleButton(
                                            it.toString()
                                        )
                                    )
                                },
                                isToday = schedule.date == state.today,
                                isEmpty = schedule.todos.isEmpty(),
                                holidays = schedule.holidays
                            )
                            Spacer(modifier = Modifier.height(CaramelTheme.spacing.s))
                        }
                        items(items = schedule.todos) { todo ->
                            BottomSheetTodoItem(
                                id = todo.id,
                                title = todo.title,
                                description = todo.description,
                                url = todo.url,
                                onClickUrl = { onIntent(CalendarIntent.ClickTodoUrl(it)) },
                                onClickTodo = {
                                    onIntent(
                                        CalendarIntent.ClickTodoItemInBottomSheet(
                                            it
                                        )
                                    )
                                }
                            ) {
                                DefaultBottomSheetTodoItem()
                            }
                            Spacer(modifier = Modifier.height(CaramelTheme.spacing.xl))
                        }
                    }
                }
            }
        ) {
            Column {
                CalendarDayOfWeek(
                    modifier = Modifier.height(height = CalendarDimension.datePickerHeight)
                )
                CaramelCalendar(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = CalendarDimension.sheetPeekHeight)
                        .background(color = CaramelTheme.color.background.primary),
                    year = state.year,
                    month = state.month,
                    schedules = state.schedules,
                    selectedDate = state.selectedDate,
                    onClickTodo = {},
                    onClickCell = { onIntent(CalendarIntent.ClickCalendarCell(it)) }
                )
            }
        }
    }
}