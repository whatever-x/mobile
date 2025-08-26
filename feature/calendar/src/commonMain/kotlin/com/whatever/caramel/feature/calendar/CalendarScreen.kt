package com.whatever.caramel.feature.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import com.whatever.caramel.core.designsystem.components.CaramelPullToRefreshIndicator
import com.whatever.caramel.core.designsystem.components.CaramelTopBar
import com.whatever.caramel.core.designsystem.themes.CaramelTheme
import com.whatever.caramel.core.domain.policy.CalendarPolicy.TOTAL_MONTH_SIZE
import com.whatever.caramel.core.domain.policy.CalendarPolicy.TOTAL_YEAR_SIZE
import com.whatever.caramel.feature.calendar.component.CalendarDatePicker
import com.whatever.caramel.feature.calendar.component.CalendarDropDownDateMenu
import com.whatever.caramel.feature.calendar.component.CalendarType
import com.whatever.caramel.feature.calendar.component.CaramelCalendar
import com.whatever.caramel.feature.calendar.mvi.CalendarIntent
import com.whatever.caramel.feature.calendar.mvi.CalendarState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun CalendarScreen(
    state: CalendarState,
    horizontalPagerState: PagerState = rememberPagerState(initialPage = state.page) { TOTAL_YEAR_SIZE * TOTAL_MONTH_SIZE },
    onIntent: (CalendarIntent) -> Unit,
) {
    val pullToRefreshState = rememberPullToRefreshState()

    LaunchedEffect(horizontalPagerState.currentPage) {
        onIntent(CalendarIntent.SwipeCalendar(page = horizontalPagerState.currentPage))
    }

    BottomSheetScaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = CaramelTheme.color.background.primary,
        topBar = {
            CaramelTopBar(
                modifier = Modifier
                    .background(color = CaramelTheme.color.background.primary)
                    .fillMaxWidth()
                    .statusBarsPadding(),
                leadingContent = {
                    CalendarDropDownDateMenu(
                        currentDate = state.currentDate,
                        isShowDropDownMenu = state.isShowDropDownMenu,
                        onClickDropDownMenu = {
                            if (state.isShowDropDownMenu) {
                                onIntent(CalendarIntent.HideDropDownMenu)
                            } else {
                                onIntent(CalendarIntent.ShowDropDownMenu)
                            }
                        },
                    )
                }
            )
        },
        sheetContent = {
            // TODO
        }
    ) { innerPadding ->
        PullToRefreshBox(
            modifier = Modifier.padding(innerPadding),
            isRefreshing = state.isRefreshing,
            state = pullToRefreshState,
            onRefresh = { onIntent(CalendarIntent.RefreshCalendar) },
            indicator = {
                CaramelPullToRefreshIndicator(
                    isRefreshing = state.isRefreshing,
                    state = pullToRefreshState
                )
            }
        ) {
            BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
                val calendarHeight = maxHeight

                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    userScrollEnabled = state.isRefreshing.not()
                ) {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .requiredHeight(calendarHeight)
                        ) {
                            CaramelCalendar(
                                type = CalendarType.HORIZONTAL,
                                pagerState = horizontalPagerState,
                            )
                        }
                    }
                }
            }
        }

        CalendarDatePicker(
            isShowDropDownMenu = state.isShowDropDownMenu,
            dateUiState = state.dateUiState,
            onYearChanged = { year -> onIntent(CalendarIntent.ChangeYearPicker(year = year)) },
            onMonthChanged = { month -> onIntent(CalendarIntent.ChangeMonthPicker(month = month)) },
            onDismiss = { onIntent(CalendarIntent.DismissDropDownMenu) },
        )
    }
}
