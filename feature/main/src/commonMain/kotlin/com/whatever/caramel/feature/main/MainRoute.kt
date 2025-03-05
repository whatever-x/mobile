package com.whatever.caramel.feature.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.whatever.caramel.feature.calendar.navigation.calendarContent
import com.whatever.caramel.feature.calendar.navigation.navigateToCalendar
import com.whatever.caramel.feature.home.navigation.HomeRoute
import com.whatever.caramel.feature.home.navigation.homeContent
import com.whatever.caramel.feature.home.navigation.navigateToHome
import com.whatever.caramel.feature.memo.navigation.memoContent
import com.whatever.caramel.feature.memo.navigation.navigateToMemo
import io.github.aakira.napier.Napier

@Composable
internal fun MainRoute(
    navigateToSetting: () -> Unit,
    navigateToStaredCoupleDay: () -> Unit,
    navigateToTodoDetail: () -> Unit,
    navigateToCreateTodo: () -> Unit,
) {
    val mainNavHostController = rememberNavController()
    var selectedItem by rememberSaveable { mutableIntStateOf(0) }
    val items = listOf("홈", "캘린더", "메모", "만들기")

    Napier.d { selectedItem.toString() }

    MainScaffold(
        bottomBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color.Cyan)
                    .navigationBarsPadding(),
            ) {
                items.forEachIndexed { index, item ->
                    NavigationBarItem(
                        icon = { Icon(Icons.Filled.Favorite, contentDescription = item) },
                        label = { Text(item) },
                        selected = selectedItem == index,
                        onClick = {
                            selectedItem = index

                            when (selectedItem) {
                                0 -> {
                                    mainNavHostController.navigateToHome {
                                        popUpTo(mainNavHostController.graph.id)
                                    }
                                }

                                1 -> {
                                    mainNavHostController.navigateToCalendar {
                                        popUpTo(mainNavHostController.graph.id)
                                    }
                                }

                                2 -> {
                                    mainNavHostController.navigateToMemo {
                                        popUpTo(mainNavHostController.graph.id)
                                    }
                                }

                                3 -> navigateToCreateTodo()
                            }
                        }
                    )
                }
            }
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