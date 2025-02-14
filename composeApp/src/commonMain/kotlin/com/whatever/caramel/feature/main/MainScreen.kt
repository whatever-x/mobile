package com.whatever.caramel.feature.main

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import cafe.adriel.voyager.core.annotation.InternalVoyagerApi
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.internal.BackHandler
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabDisposable
import cafe.adriel.voyager.navigator.tab.TabNavigator
import com.whatever.caramel.core.presentation.closeApp
import com.whatever.caramel.feature.main.calendar.CalendarTab
import com.whatever.caramel.feature.main.editor.EditorTab
import com.whatever.caramel.feature.main.home.HomeTab
import com.whatever.caramel.feature.main.memo.MemoTab

class MainScreenRoot : Screen {
    @Composable
    override fun Content() {
        MainScreen()
    }
}

@OptIn(InternalVoyagerApi::class)
@Composable
fun MainScreen() {
    TabNavigator(HomeTab,
        tabDisposable = {
            TabDisposable(
                navigator = it,
                tabs = listOf(HomeTab, CalendarTab, MemoTab, EditorTab)
            )
        }
    ){
        Scaffold(
            content = {
                CurrentTab()
            },
            bottomBar = {
                BottomNavigation {
                    TabNavigation(HomeTab)
                    TabNavigation(CalendarTab)
                    TabNavigation(MemoTab)
                    TabNavigation(EditorTab)
                }
            }
        )
    }
}

@Composable
fun RowScope.TabNavigation(tab : Tab) {
    val tabNavigator = LocalTabNavigator.current

    BottomNavigationItem(
        selectedContentColor = Color.White,
        selected  = tabNavigator.current.key == tab.key,
        onClick = { tabNavigator.current = tab },
        icon = { Icon(painter = tab.options.icon!!, contentDescription = tab.options.title) },
        label = { Text(tab.options.title) }
    )
}