package com.whatever.caramel.feature.main

import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.whatever.caramel.core.designsystem.components.BottomNavItem
import com.whatever.caramel.core.designsystem.components.CaramelBottomNavigationWithTrailingButton
import com.whatever.caramel.core.designsystem.components.CaramelNavItemCreateButton
import com.whatever.caramel.core.domain.vo.content.ContentType
import com.whatever.caramel.feature.calendar.navigation.calendarContent
import com.whatever.caramel.feature.calendar.navigation.navigateToCalendar
import com.whatever.caramel.feature.home.navigation.HomeRoute
import com.whatever.caramel.feature.home.navigation.homeContent
import com.whatever.caramel.feature.home.navigation.navigateToHome
import com.whatever.caramel.feature.memo.navigation.memoContent
import com.whatever.caramel.feature.memo.navigation.navigateToMemo

@Composable
internal fun MainRoute(
    navigateToSetting: () -> Unit,
    navigateToStaredCoupleDay: () -> Unit,
    navigateToTodoDetail: (Long, ContentType) -> Unit,
    navigateToCreateTodo: () -> Unit,
) {
    val hapticFeedback = LocalHapticFeedback.current
    val mainNavHostController = rememberNavController()
    var currentItem by rememberSaveable { mutableStateOf(BottomNavItem.HOME) }

    MainScaffold(
        bottomBar = {
            CaramelBottomNavigationWithTrailingButton(
                modifier = Modifier.navigationBarsPadding(),
                currentItem = currentItem,
                onClickNavItem = { bottomNavItem ->
                    currentItem = bottomNavItem
                    hapticFeedback.performHapticFeedback(HapticFeedbackType.LongPress)

                    when (bottomNavItem) {
                        BottomNavItem.HOME -> {
                            mainNavHostController.navigateToHome {
                                popUpTo(mainNavHostController.graph.id)
                            }
                        }

                        BottomNavItem.CALENDAR -> {
                            mainNavHostController.navigateToCalendar {
                                popUpTo(mainNavHostController.graph.id)
                            }
                        }

                        BottomNavItem.MEMO -> {
                            mainNavHostController.navigateToMemo {
                                popUpTo(mainNavHostController.graph.id)
                            }
                        }
                    }
                },
                trailingButton = {
                    CaramelNavItemCreateButton(
                        onClickButton = { navigateToCreateTodo() }
                    )
                }
            )
        }
    ) { innerPadding ->
        NavHost(
            modifier = Modifier.padding(paddingValues = innerPadding),
            navController = mainNavHostController,
            startDestination = HomeRoute
        ) {
            homeContent(
                navigateToSetting = navigateToSetting,
                navigateToStaredCoupleDay = navigateToStaredCoupleDay,
                navigateToTodoDetail = navigateToTodoDetail,
                navigateToCreateTodo = navigateToCreateTodo
            )
            calendarContent(
                navigateToCreateTodo = navigateToCreateTodo,
                navigateToTodoDetail = navigateToTodoDetail,
            )
            memoContent(
                navigateToTodoDetail = navigateToTodoDetail
            )
        }
    }

}