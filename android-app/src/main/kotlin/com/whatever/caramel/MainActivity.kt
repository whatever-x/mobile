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
import io.github.aakira.napier.Napier
import org.koin.android.ext.android.inject
import org.koin.android.scope.AndroidScopeComponent
import org.koin.androidx.scope.activityScope
import org.koin.core.parameter.parametersOf
import org.koin.core.scope.Scope

class MainActivity :
    ComponentActivity(),
    AndroidScopeComponent {
    private val deepLinkHandler: DeepLinkHandler by inject()
    override val scope: Scope by activityScope()
    private val inAppReview: CaramelInAppReview by scope.inject { parametersOf(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setupSystemBars()

        initAppsFlyer()

        setContent {
            val navHostController = rememberNavController()

            CaramelComposeApp(
                navHostController = navHostController,
                inAppReview = inAppReview,
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
