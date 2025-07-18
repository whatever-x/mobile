package com.whatever.caramel.feature.calendar

import androidx.compose.animation.core.animateIntAsState
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
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetValue
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import com.whatever.caramel.core.designsystem.components.CaramelPullToRefreshIndicator
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
import kotlinx.collections.immutable.toImmutableList
import kotlinx.datetime.Month
import kotlin.math.roundToInt

// @RyuSw-cs 2025.05.24 TODO : Column + ModalBottomSheet 조합으로 변경
@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun CalendarScreen(
    state: CalendarState,
    onIntent: (CalendarIntent) -> Unit,
) {
    val pagerState =
        rememberPagerState(initialPage = state.pageIndex) { Calendar.yearSize * Month.entries.size }
    val bottomSheetState = rememberStandardBottomSheetState()
    val bottomSheetScaffoldState =
        rememberBottomSheetScaffoldState(bottomSheetState = bottomSheetState)
    val lazyListState = rememberLazyListState()
    val pullToRefreshState = rememberPullToRefreshState()
    val verticalScrollState = rememberScrollState()
    val calendarScreenOffset by animateIntAsState(
        targetValue =
            when {
                state.isRefreshing -> 250
                state.bottomSheetState == BottomSheetState.EXPANDED -> 0
                pullToRefreshState.distanceFraction in 0f..1f -> (250 * pullToRefreshState.distanceFraction).roundToInt()
                pullToRefreshState.distanceFraction > 1f -> (250 + ((pullToRefreshState.distanceFraction - 1f) * 1f) * 100).roundToInt()
                else -> 0
            },
    )

    LaunchedEffect(state.selectedDate) {
        if (state.bottomSheetState == BottomSheetState.EXPANDED) {
            val scheduleIndex = state.yearSchedule.indexOfFirst { it.date == state.selectedDate }
            if (scheduleIndex >= 0) {
                val itemPosition =
                    scheduleIndex +
                        state.yearSchedule.take(scheduleIndex).sumOf { it.todos.size }
                lazyListState.scrollToItem(index = itemPosition)
            }
        }
    }
    LaunchedEffect(state.bottomSheetState) {
        when (state.bottomSheetState) {
            BottomSheetState.HIDDEN -> bottomSheetState.hide()
            BottomSheetState.EXPANDED -> bottomSheetState.expand()
            BottomSheetState.PARTIALLY_EXPANDED -> {
                lazyListState.scrollToItem(0)
                bottomSheetState.partialExpand()
            }
        }
    }
    LaunchedEffect(bottomSheetState.currentValue) {
        val updateStateValue =
            when (bottomSheetState.currentValue) {
                SheetValue.Hidden -> BottomSheetState.HIDDEN
                SheetValue.Expanded -> BottomSheetState.EXPANDED
                SheetValue.PartiallyExpanded -> BottomSheetState.PARTIALLY_EXPANDED
            }
        onIntent(CalendarIntent.DraggingCalendarBottomSheet(false))
        onIntent(CalendarIntent.UpdateCalendarBottomSheet(updateStateValue))
    }

    LaunchedEffect(bottomSheetState.targetValue) {
        val isDragging = bottomSheetState.currentValue != bottomSheetState.targetValue
        onIntent(CalendarIntent.DraggingCalendarBottomSheet(isDragging))
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

    PullToRefreshBox(
        modifier =
            Modifier
                .background(color = CaramelTheme.color.background.primary)
                .statusBarsPadding(),
        state = pullToRefreshState,
        isRefreshing = state.isRefreshing,
        onRefresh = { onIntent(CalendarIntent.RefreshCalendar) },
        indicator = {
            CaramelPullToRefreshIndicator(
                state = pullToRefreshState,
                isRefreshing = state.isRefreshing,
            )
        },
    ) {
        BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
            val totalHeight = maxHeight - CalendarDimension.sheetPeekHeight
            BottomSheetScaffold(
                scaffoldState = bottomSheetScaffoldState,
                sheetPeekHeight = CalendarDimension.sheetPeekHeight,
                sheetShape =
                    RoundedCornerShape(
                        topStart = CalendarDimension.sheetTopStartCornerRadius,
                        topEnd = CalendarDimension.sheetTopEndCornerRadius,
                    ),
                sheetContainerColor = CaramelTheme.color.background.primary,
                sheetContentColor = CaramelTheme.color.background.tertiary,
                sheetDragHandle = {
                    CaramelBottomSheetHandle(topDescVisibility = state.isBottomSheetTopDescVisible)
                },
                topBar = {
                    CaramelTopBar(
                        modifier =
                            Modifier
                                .background(color = CaramelTheme.color.background.primary)
                                .clickable(
                                    indication = null,
                                    interactionSource = null,
                                    onClick = { onIntent(CalendarIntent.ClickOutSideBottomSheet) },
                                ),
                        leadingContent = {
                            CurrentDateMenu(
                                year = state.year,
                                month = state.month,
                                isShowDropMenu = state.isShowDatePicker,
                                onClickDatePicker = { onIntent(CalendarIntent.ClickDatePicker) },
                            )
                        },
                    )
                },
                sheetContent = {
                    val availableHeight =
                        totalHeight - CalendarDimension.datePickerHeight - CalendarDimension.dayOfWeekHeight +
                            CalendarDimension.sheetPartiallyExpandedTextHeight
                    LazyColumn(
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .background(color = CaramelTheme.color.background.tertiary)
                                .padding(
                                    top = CaramelTheme.spacing.xs,
                                    bottom = CaramelTheme.spacing.l,
                                    start = CaramelTheme.spacing.xl,
                                    end = CaramelTheme.spacing.xl,
                                ).height(availableHeight),
                        state = lazyListState,
                    ) {
                        state.monthSchedule.forEach { schedule ->
                            item {
                                BottomSheetTodoListHeader(
                                    date = schedule.date,
                                    onClickAddSchedule = {
                                        onIntent(CalendarIntent.ClickAddScheduleButton(schedule.date))
                                    },
                                    isToday = schedule.date == state.today,
                                    isEmpty = schedule.todos.isEmpty(),
                                    holidays = schedule.holidays,
                                    anniversaries = schedule.anniversaries,
                                )
                                Spacer(modifier = Modifier.height(CaramelTheme.spacing.s))
                            }
                            items(
                                items = schedule.todos,
                                key = { todo ->
                                    todo.id
                                },
                            ) { todo ->
                                BottomSheetTodoItem(
                                    id = todo.id,
                                    title = todo.title,
                                    description = todo.description,
                                    url = todo.url,
                                    onClickUrl = { onIntent(CalendarIntent.ClickTodoUrl(it)) },
                                    onClickTodo = {
                                        onIntent(
                                            CalendarIntent.ClickTodoItemInBottomSheet(
                                                it,
                                            ),
                                        )
                                    },
                                ) {
                                    DefaultBottomSheetTodoItem()
                                }
                                Spacer(modifier = Modifier.height(CaramelTheme.spacing.xl))
                            }
                        }
                    }
                },
            ) {
                Box(
                    modifier =
                        Modifier
                            .fillMaxSize()
                            .background(color = CaramelTheme.color.background.primary),
                ) {
                    Column(
                        modifier =
                            Modifier
                                .fillMaxSize()
                                .graphicsLayer {
                                    translationY = calendarScreenOffset.toFloat()
                                }.verticalScroll(verticalScrollState),
                    ) {
                        CalendarDayOfWeek(
                            modifier =
                                Modifier
                                    .height(height = CalendarDimension.dayOfWeekHeight)
                                    .clickable(
                                        indication = null,
                                        interactionSource = null,
                                        onClick = { onIntent(CalendarIntent.ClickOutSideBottomSheet) },
                                    ),
                        )
                        val availableCalendarHeight =
                            totalHeight - CalendarDimension.datePickerHeight - CalendarDimension.dayOfWeekHeight

                        HorizontalPager(
                            modifier = Modifier.height(height = availableCalendarHeight),
                            state = pagerState,
                            beyondViewportPageCount = 2,
                        ) { pageIndex ->
                            CaramelCalendar(
                                modifier =
                                    Modifier.background(color = CaramelTheme.color.background.primary),
                                pageIndex = pageIndex,
                                schedules = state.yearSchedule.toImmutableList(),
                                selectedDate = state.selectedDate,
                                onClickTodo = { onIntent(CalendarIntent.ClickTodoItemInCalendar(it)) },
                                onClickCell = { onIntent(CalendarIntent.ClickCalendarCell(it)) },
                            )
                        }
                    }

                    CalendarDatePicker(
                        year = state.pickerDate.year,
                        month = Month.entries[state.pickerDate.month - 1],
                        isShowDropMenu = state.isShowDatePicker,
                        onDismiss = { onIntent(CalendarIntent.ClickDatePickerOutSide) },
                        onYearChanged = { onIntent(CalendarIntent.UpdateSelectPickerYear(it)) },
                        onMonthChanged = { onIntent(CalendarIntent.UpdateSelectPickerMonth(it)) },
                    )
                }
            }
        }
    }
}
