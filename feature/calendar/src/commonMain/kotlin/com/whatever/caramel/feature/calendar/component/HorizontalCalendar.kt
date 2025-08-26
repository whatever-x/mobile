package com.whatever.caramel.feature.calendar.component

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.whatever.caramel.core.domain.policy.CalendarPolicy.MIN_YEAR

@Composable
internal fun HorizontalCalendar(
    modifier: Modifier = Modifier,
    state: PagerState,
) {
    HorizontalPager(
        modifier = modifier,
        state = state,
        beyondViewportPageCount = 2
    ) { page ->
        val year by remember { derivedStateOf { MIN_YEAR + (page / 12) } }
        val month by remember { derivedStateOf { (page % 12) + 1 } }

        MonthCalendar(
            modifier = Modifier.fillMaxSize(),
            year = year,
            month = month
        )
    }
}
