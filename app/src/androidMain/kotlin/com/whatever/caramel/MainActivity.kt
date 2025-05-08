package com.whatever.caramel

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.appsflyer.AppsFlyerLib
import com.appsflyer.deeplink.DeepLink
import com.appsflyer.deeplink.DeepLinkResult
import com.whatever.caramel.app.CaramelComposeApp
import com.whatever.caramel.app.CaramelViewModel
import com.whatever.caramel.core.domain.exception.CaramelException
import com.whatever.caramel.deeplink.CaramelAppsFlyerDeepLinkValues
import com.whatever.caramel.mvi.AppIntent
import io.github.aakira.napier.Napier
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {

    private val viewModel: CaramelViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        AppsFlyerLib.getInstance().subscribeForDeepLink { deeplinkResult ->
            when (deeplinkResult.status) {
                DeepLinkResult.Status.NOT_FOUND -> Napier.d { "Deep link not found" }
                DeepLinkResult.Status.ERROR -> Napier.d { "Deep link error" }
                DeepLinkResult.Status.FOUND -> {
                    val deepLinkObj: DeepLink = deeplinkResult.deepLink

                    try {
                        val appsFlyerDeepLinkValue = deepLinkObj.deepLinkValue ?: return@subscribeForDeepLink
                        val deepLinkValue = CaramelAppsFlyerDeepLinkValues.valueOf(appsFlyerDeepLinkValue)

                        when (deepLinkValue) {
                            CaramelAppsFlyerDeepLinkValues.INVITE_CODE -> {
                                val inviteCode = deepLinkObj.getStringValue(deepLinkValue.path)

                                if (inviteCode != null) {
                                    viewModel.intent(AppIntent.AcceptInvitation(inviteCode = inviteCode))
                                }
                            }
                        }
                    } catch (e: Exception) {
                        Napier.d { "DeepLink data came back null" }
                    }
                }
            }
        }

        setContent {
            val navHostController = rememberNavController()

            CaramelComposeApp(
                navHostController = navHostController,
                viewModel = viewModel
            )
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)

        try {
            if (intent.data != null) {
                val intentData = intent.data.toString()
                Napier.d { intentData }

                viewModel.intent(AppIntent.ReceiveNewIntentData(data = intentData))
            } else {
                throw CaramelException(
                    code = "1000", // @ham2174 FIXME : 인텐트 데이터가 null 인 경우의 에러 코드 정의
                    message = "intent data came back null",
                    debugMessage = "intent data came back null"
                )
            }
        } catch (e: Exception) {
            Napier.d { "intent data came back null" }
        }
    }

}