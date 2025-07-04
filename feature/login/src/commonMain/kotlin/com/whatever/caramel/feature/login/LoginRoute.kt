package com.whatever.caramel.feature.login

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.whatever.caramel.core.domain.vo.auth.SocialLoginType
import com.whatever.caramel.core.domain.vo.user.UserStatus
import com.whatever.caramel.feature.login.mvi.LoginIntent
import com.whatever.caramel.feature.login.mvi.LoginSideEffect
import com.whatever.caramel.feature.login.social.SocialAuthenticator
import com.whatever.caramel.feature.login.social.apple.AppleAuthProvider
import com.whatever.caramel.feature.login.social.apple.AppleUser
import com.whatever.caramel.feature.login.social.kakao.KakaoAuthProvider
import com.whatever.caramel.feature.login.social.kakao.KakaoUser
import com.whatever.caramel.feature.login.util.Platform
import dev.icerock.moko.permissions.DeniedAlwaysException
import dev.icerock.moko.permissions.DeniedException
import dev.icerock.moko.permissions.Permission
import dev.icerock.moko.permissions.PermissionState
import dev.icerock.moko.permissions.RequestCanceledException
import dev.icerock.moko.permissions.compose.BindEffect
import dev.icerock.moko.permissions.compose.rememberPermissionsControllerFactory
import dev.icerock.moko.permissions.notifications.REMOTE_NOTIFICATION
import io.github.aakira.napier.Napier
import kotlinx.coroutines.launch
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun LoginRoute(
    viewModel: LoginViewModel = koinViewModel(),
    kakaoAuthenticator: SocialAuthenticator<KakaoUser> = koinInject<KakaoAuthProvider>().get(),
    appleAuthenticator: SocialAuthenticator<AppleUser>? = if (Platform.isIos) koinInject<AppleAuthProvider>().get() else null,
    navigateToStartDestination: (UserStatus) -> Unit,
    showErrorDialog: (String, String?) -> Unit,
    showErrorToast: (String) -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val scope = rememberCoroutineScope()
    val socialAuthLaunch: (SocialLoginType) -> Unit =
        remember {
            { type ->
                scope.launch {
                    when (type) {
                        SocialLoginType.KAKAO -> {
                            val result = kakaoAuthenticator.authenticate()
                            viewModel.intent(LoginIntent.ClickKakaoLoginButton(result = result))
                        }

                        SocialLoginType.APPLE -> {
                            val result = appleAuthenticator!!.authenticate()
                            viewModel.intent(LoginIntent.ClickAppleLoginButton(result = result))
                        }
                    }
                }
            }
        }
    val permissionFactory = rememberPermissionsControllerFactory()
    val permissionsController = remember(permissionFactory) { permissionFactory.createPermissionsController() }

    BindEffect(permissionsController = permissionsController)

    LaunchedEffect(Unit) {
        val permissionState: PermissionState =
            permissionsController.getPermissionState(Permission.REMOTE_NOTIFICATION)

        when (permissionState) {
            // case 1. 초기 유저가 팝업이 떳을 때의 현재 상태
            // 안드로이드 : NotGranted
            // iOS : NotDetermined
            // case 2. 유저가 팝업을 허용 했을 때
            // 안드로이드, iOS : Granted
            // case 3. 유저가 팝업을 허용 안함 했을 때
            // 안드로이드 : Denied
            // iOS : DeniedAlways

            PermissionState.Granted -> {
                Napier.d { "Notifiaction 퍼미션 허용함." }
            }

            PermissionState.Denied, // 안드로이드 유저가 팝업을 허용 안함 선택 했을 때
            PermissionState.DeniedAlways,
            -> { // iOS 유저가 팝업을 허용 안함 선택 했을 때
                // TODO : 알림 허용 안내 가이드 필요
//                permissionsController.openAppSettings()
            }

            PermissionState.NotGranted, // 안드로이드 초기 팝업 권한이 떳을 경우
            PermissionState.NotDetermined,
            -> { // iOS 초기 팝업 권한이 떳을 경우
                try {
                    permissionsController.providePermission(Permission.REMOTE_NOTIFICATION)
                } catch (e: DeniedException) {
                    Napier.d { e.permission.toString() }
                } catch (e: DeniedAlwaysException) {
                    Napier.d { e.message.toString() }
                } catch (e: RequestCanceledException) {
                    Napier.d { e.message.toString() }
                } catch (e: Exception) {
                    Napier.e { e.message.toString() }
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect { sideEffect ->
            when (sideEffect) {
                is LoginSideEffect.ShowErrorToast -> showErrorToast(sideEffect.message)
                is LoginSideEffect.NavigateToStartDestination -> navigateToStartDestination(sideEffect.userStatus)
                is LoginSideEffect.ShowErrorDialog -> showErrorDialog(sideEffect.message, sideEffect.description)
            }
        }
    }

    LoginScreen(
        state = state,
        onIntent = { intent -> viewModel.intent(intent) },
        onLaunch = { socialType -> socialAuthLaunch(socialType) },
    )
}
