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
import com.whatever.caramel.core.deeplink.DeepLinkHandler
import com.whatever.caramel.core.deeplink.model.AppsFlyerDeepLinkParameter
import io.github.aakira.napier.Napier
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {

    private val deepLinkHandler: DeepLinkHandler by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        initAppsFlyer()

        setContent {
            val navHostController = rememberNavController()

            CaramelComposeApp(
                navHostController = navHostController,
            )
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)

        setIntent(intent)
    }

    private fun initAppsFlyer() {
        AppsFlyerLib.getInstance().subscribeForDeepLink { deeplinkResult ->
            when (deeplinkResult.status) {
                DeepLinkResult.Status.NOT_FOUND -> Napier.d { "Deep link not found" }
                DeepLinkResult.Status.ERROR -> Napier.d { "Deep link error" }
                DeepLinkResult.Status.FOUND -> {
                    val deepLinkObj: DeepLink = deeplinkResult.deepLink
                    val deepLinkValue = deepLinkObj.deepLinkValue ?: return@subscribeForDeepLink

                    val params: Map<AppsFlyerDeepLinkParameter, String?> =
                        AppsFlyerDeepLinkParameter.entries.associateWith {
                            deepLinkObj.getStringValue(it.parameterName)
                        }

                    deepLinkHandler.handleAppsFlyerData(
                        deepLinkValue = deepLinkValue,
                        params = params
                    )
                }
            }
        }
    }

}