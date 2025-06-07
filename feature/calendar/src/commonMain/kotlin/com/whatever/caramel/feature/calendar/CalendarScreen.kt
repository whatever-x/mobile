package com.whatever.caramel.feature.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetValue
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import com.whatever.caramel.core.designsystem.components.CaramelTopBar
import com.whatever.caramel.core.designsystem.themes.CaramelTheme
import com.whatever.caramel.core.domain.vo.calendar.Calendar
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
import kotlinx.datetime.Month

// @RyuSw-cs 2025.05.24 TODO : Column + ModalBottomSheet 조합으로 변경
@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun CalendarScreen(
    state: CalendarState,
    onIntent: (CalendarIntent) -> Unit
) {
    val pagerState =
        rememberPagerState(initialPage = state.pageIndex) { Calendar.yearSize * Month.entries.size }
    val bottomSheetState = rememberStandardBottomSheetState()
    val bottomSheetScaffoldState =
        rememberBottomSheetScaffoldState(bottomSheetState = bottomSheetState)
    val lazyListState = rememberLazyListState()

    LaunchedEffect(state.selectedDate) {
        if (state.bottomSheetState == BottomSheetState.EXPANDED) {
            val scheduleIndex = state.monthSchedules.indexOfFirst { it.date == state.selectedDate }
            if (scheduleIndex >= 0) {
                val itemPosition = scheduleIndex +
                        state.monthSchedules.take(scheduleIndex).sumOf { it.todos.size }
                lazyListState.scrollToItem(index = itemPosition)
            }
        }
    }
    LaunchedEffect(state.bottomSheetState) {
        when (state.bottomSheetState) {
            BottomSheetState.HIDDEN -> bottomSheetState.hide()
            BottomSheetState.EXPANDED -> bottomSheetState.expand()
            BottomSheetState.PARTIALLY_EXPANDED -> bottomSheetState.partialExpand()
        }
    }
    LaunchedEffect(bottomSheetState.currentValue) {
        val updateStateValue = when (bottomSheetState.currentValue) {
            SheetValue.Hidden -> BottomSheetState.HIDDEN
            SheetValue.Expanded -> BottomSheetState.EXPANDED
            SheetValue.PartiallyExpanded -> BottomSheetState.PARTIALLY_EXPANDED
        }
        onIntent(CalendarIntent.ToggleCalendarBottomSheet(updateStateValue))
    }

    LaunchedEffect(pagerState.currentPage) {
        if (pagerState.currentPage != state.pageIndex) {
            onIntent(CalendarIntent.UpdatePageIndex(pagerState.currentPage))
        }
    }
    LaunchedEffect(state.pageIndex) {
        if (state.pageIndex != pagerState.currentPage) {
            pagerState.scrollToPage(state.pageIndex)
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
                CaramelTopBar(
                    modifier = Modifier
                        .background(color = CaramelTheme.color.background.primary)
                        .clickable(
                            indication = null,
                            interactionSource = null,
                            onClick = { onIntent(CalendarIntent.ClickOutSideBottomSheet) }
                        ),
                    leadingContent = {
                        CurrentDateMenu(
                            year = state.year,
                            month = state.month,
                            isShowDropMenu = state.isShowDatePicker,
                            onClickDatePicker = { onIntent(CalendarIntent.ClickDatePicker) }
                        )
                    }
                )
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
                        .height(availableHeight),
                    state = lazyListState
                ) {
                    state.monthSchedules.forEach { schedule ->
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
                                holidays = schedule.holidays,
                                anniversaries = schedule.anniversaries
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
            Box(modifier = Modifier.fillMaxSize()) {
                Column {
                    CalendarDayOfWeek(
                        modifier = Modifier
                            .height(height = CalendarDimension.datePickerHeight)
                            .clickable(
                                indication = null,
                                interactionSource = null,
                                onClick = { onIntent(CalendarIntent.ClickOutSideBottomSheet) }
                            )
                    )

                    HorizontalPager(
                        modifier = Modifier.fillMaxSize(),
                        state = pagerState
                    ) {
                        CaramelCalendar(
                            modifier = Modifier
                                .weight(1f)
                                .padding(bottom = CalendarDimension.sheetPeekHeight)
                                .background(color = CaramelTheme.color.background.primary),
                            year = state.year,
                            month = state.month,
                            schedules = state.monthSchedules,
                            selectedDate = state.selectedDate,
                            onClickTodo = { onIntent(CalendarIntent.ClickTodoItemInCalendar(it)) },
                            onClickCell = { onIntent(CalendarIntent.ClickCalendarCell(it)) }
                        )
                    }
                }

                CalendarDatePicker(
                    year = state.pickerDate.year,
                    month = Month.entries[state.pickerDate.month - 1],
                    isShowDropMenu = state.isShowDatePicker,
                    onDismiss = { onIntent(CalendarIntent.ClickDatePickerOutSide) },
                    onYearChanged = { onIntent(CalendarIntent.UpdateSelectPickerYear(it)) },
                    onMonthChanged = { onIntent(CalendarIntent.UpdateSelectPickerMonth(it)) }
                )
            }
        }
    }
}