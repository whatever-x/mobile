package com.whatever.caramel.feature.splash

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cafe.adriel.voyager.core.registry.rememberScreen
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.whatever.caramel.app.Route
import com.whatever.caramel.core.presentation.DesertWhite
import com.whatever.caramel.feature.temp.SampleNavigateButton
import org.koin.compose.viewmodel.koinViewModel

class SplashScreenRoot: Screen {
    @Composable
    override fun Content() {
        val viewModel = koinViewModel<SplashViewModel>()
        val state by viewModel.state.collectAsStateWithLifecycle()
        SplashScreen(
            state = state
        )
    }
}

@Composable
fun SplashScreen(
    modifier: Modifier = Modifier,
    state: SplashState
) {
    val navigateList = listOf("onboarding", "login", "profile", "couple", "home")
    val navigator = LocalNavigator.currentOrThrow
    val onBoardingScreen = rememberScreen(Route.Onboarding)
    val loginScreen = rememberScreen(provider = Route.Login)
    val profileScreen = rememberScreen(provider = Route.ProfileBirth)
    val coupleScreen = rememberScreen(provider = Route.CoupleInvite)
    val homeScreen = rememberScreen(provider = Route.Home)

    Surface(
        color = DesertWhite,
        modifier = modifier
            .fillMaxSize()
            .padding(all = 16.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center,
        ){
            if(state.isLoading){
                CircularProgressIndicator()
            }
        }

        when {
            state.isFirst -> navigator.replace(onBoardingScreen)
            state.isSuccess -> navigator.push(homeScreen)
            state.needLogin -> navigator.push(loginScreen)
            state.needPermission -> {
                // do something..
            }
            state.needCouple -> navigator.push(coupleScreen)
            state.needProfile -> navigator.push(profileScreen)
        }

        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier
                    .wrapContentHeight()
                    .fillMaxWidth(),
                color = Color.Black,
                fontSize = TextUnit.Unspecified,
                text = "Splash"
            )
            Spacer(modifier = Modifier.height(16.dp))
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
            ) {
                items(navigateList) { item ->
                    SampleNavigateButton(
                        modifier = Modifier.padding(bottom = 8.dp),
                        content = item,
                        onClick = {
                            when (item) {
                                "onboarding" -> navigator.push(onBoardingScreen)
                                "login" -> navigator.push(loginScreen)
                                "profile" -> navigator.push(profileScreen)
                                "couple" -> navigator.push(coupleScreen)
                                "home" -> navigator.push(homeScreen)
                            }
                        }
                    )
                }
            }
        }
    }
}