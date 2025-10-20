package com.whatever.caramel

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.view.WindowCompat
import androidx.navigation.compose.rememberNavController
import com.appsflyer.AppsFlyerLib
import com.appsflyer.deeplink.DeepLink
import com.appsflyer.deeplink.DeepLinkResult
import com.whatever.caramel.app.CaramelComposeApp
import com.whatever.caramel.core.deeplink.DeepLinkHandler
import com.whatever.caramel.core.deeplink.model.AppsFlyerDeepLinkParameter
import com.whatever.caramel.core.inAppReview.CaramelInAppReview
import com.whatever.caramel.core.inAppReview.CaramelInAppReviewImpl
import io.github.aakira.napier.Napier
import org.koin.android.ext.android.inject
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

class MainActivity : ComponentActivity() {
    private val deepLinkHandler: DeepLinkHandler by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setupSystemBars()

        initAppsFlyer()

        loadKoinModules(module {
            single<CaramelInAppReview> { CaramelInAppReviewImpl(activityProvider = { this@MainActivity }) }
        })
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
                        params = params,
                    )
                }
            }
        }
    }

    private fun setupSystemBars() {
        val windowInsetsController = WindowCompat.getInsetsController(window, window.decorView)

        windowInsetsController.isAppearanceLightStatusBars = true
        windowInsetsController.isAppearanceLightNavigationBars = true

        window.navigationBarColor = android.graphics.Color.TRANSPARENT
        window.statusBarColor = android.graphics.Color.TRANSPARENT
    }
}
