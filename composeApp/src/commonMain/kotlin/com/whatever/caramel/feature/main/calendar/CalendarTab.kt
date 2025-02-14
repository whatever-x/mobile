package com.whatever.caramel.feature.main.calendar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import app.composeapp.generated.resources.Res
import app.composeapp.generated.resources.test_tab_item2
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import org.jetbrains.compose.resources.stringResource

object CalendarTab : Tab{
    override val options: TabOptions
        @Composable
        get() {
            val title = stringResource(Res.string.test_tab_item2)
            val icon = rememberVectorPainter(Icons.Default.DateRange)

            return remember {
                TabOptions(
                    index = 1u,
                    title = title,
                    icon = icon
                )
            }
        }

    @Composable
    override fun Content() {
        Navigator(CalendarScreenRoot())
    }
}