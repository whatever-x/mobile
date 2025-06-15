package com.whatever.caramel.feature.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
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
import androidx.lifecycle.Lifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.whatever.caramel.core.designsystem.components.BottomNavItem
import com.whatever.caramel.core.designsystem.components.CaramelBottomNavigationWithTrailingButton
import com.whatever.caramel.core.designsystem.components.CaramelNavItemCreateButton
import com.whatever.caramel.core.designsystem.themes.CaramelTheme
import com.whatever.caramel.core.domain.vo.content.ContentType
import com.whatever.caramel.core.ui.util.ObserveLifecycleEvent
import com.whatever.caramel.feature.calendar.navigation.calendarContent
import com.whatever.caramel.feature.calendar.navigation.navigateToCalendar
import com.whatever.caramel.feature.home.navigation.HomeRoute
import com.whatever.caramel.feature.home.navigation.homeContent
import com.whatever.caramel.feature.home.navigation.navigateToHome
import com.whatever.caramel.feature.memo.navigation.memoContent
import com.whatever.caramel.feature.memo.navigation.navigateToMemo
import dev.icerock.moko.permissions.compose.PermissionsControllerFactory
import dev.icerock.moko.permissions.compose.rememberPermissionsControllerFactory
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
internal fun MainRoute(
    permissionsControllerFactory: PermissionsControllerFactory = rememberPermissionsControllerFactory(),
    viewModel: MainViewModel = koinViewModel {
        parametersOf(permissionsControllerFactory.createPermissionsController())
    },
    navigateToSetting: () -> Unit,
    navigateToStaredCoupleDay: () -> Unit,
    navigateToTodoDetail: (Long, ContentType) -> Unit,
    navigateToCreateTodo: (ContentType) -> Unit,
    navigateToCreateSchedule : (ContentType, String) -> Unit,
) {
    val hapticFeedback = LocalHapticFeedback.current
    val mainNavHostController = rememberNavController()
    var currentItem by rememberSaveable { mutableStateOf(BottomNavItem.HOME) }

    ObserveLifecycleEvent { event ->
        if (event == Lifecycle.Event.ON_RESUME) {
            viewModel.updateFcmToken()
            viewModel.updateUserSetting()
        }
    }

    MainScaffold(
        bottomBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = CaramelTheme.color.background.tertiary),
            ) {
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
                            onClickButton = { navigateToCreateTodo(ContentType.MEMO) }
                        )
                    }
                )
            }
        }
    ) { innerPadding ->
        NavHost(
            modifier = Modifier.padding(paddingValues = innerPadding),
            navController = mainNavHostController,
            startDestination = HomeRoute,
            enterTransition = { EnterTransition.None },
            exitTransition = { ExitTransition.None },
            popEnterTransition = { EnterTransition.None},
            popExitTransition = { ExitTransition.None },
        ) {
            homeContent(
                navigateToSetting = navigateToSetting,
                navigateToStaredCoupleDay = navigateToStaredCoupleDay,
                navigateToTodoDetail = navigateToTodoDetail,
                navigateToCreateTodo = navigateToCreateTodo
            )
            calendarContent(
                navigateToCreateSchedule = navigateToCreateSchedule,
                navigateToTodoDetail = navigateToTodoDetail,
            )
            memoContent(
                navigateToTodoDetail = navigateToTodoDetail
            )
        }
    }

}