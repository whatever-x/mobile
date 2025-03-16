package com.whatever.caramel.feature.login.social.apple

import androidx.compose.runtime.Composable
import androidx.compose.ui.uikit.LocalUIViewController
import com.whatever.caramel.feature.login.social.SocialAuthResult
import com.whatever.caramel.feature.login.social.SocialAuthenticator
import kotlinx.cinterop.BetaInteropApi
import org.koin.core.component.KoinComponent
import platform.AuthenticationServices.ASAuthorization
import platform.AuthenticationServices.ASAuthorizationAppleIDCredential
import platform.AuthenticationServices.ASAuthorizationAppleIDProvider
import platform.AuthenticationServices.ASAuthorizationController
import platform.AuthenticationServices.ASAuthorizationControllerDelegateProtocol
import platform.AuthenticationServices.ASAuthorizationControllerPresentationContextProvidingProtocol
import platform.AuthenticationServices.ASAuthorizationScopeEmail
import platform.AuthenticationServices.ASAuthorizationScopeFullName
import platform.AuthenticationServices.ASPresentationAnchor
import platform.Foundation.NSError
import platform.Foundation.NSString
import platform.Foundation.NSUTF8StringEncoding
import platform.Foundation.create
import platform.UIKit.UIViewController
import platform.darwin.NSObject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

internal class AppleAuthProviderImpl : AppleAuthProvider {

    @Composable
    override fun get(): SocialAuthenticator<AppleUser> {
        val viewController = LocalUIViewController.current
        return AppleAuthenticatorImpl(viewController = viewController)
    }

}

/**
 * Swift에 정의된 인증 실패시 발생 되는 에러 코드 [공식문서](https://developer.apple.com/documentation/authenticationservices/asauthorizationerror-swift.struct/code)
 * @author GunHyung-Ham
 * @since 2025.03.09
 */
enum class ASAuthorizationErrorCode(val code: Int) {
    FAILED(1000),
    CANCELED(1001),
    NOT_HANDLED(1002),
    INVALID_RESPONSE(1003),
    NOT_INTERACTIVE(1004),
    UNKNOWN(1005),
    CREDENTIAL_EXPORT(1006),
    CREDENTIAL_IMPORT(1007),
    ;
}

private class AppleAuthenticatorImpl(
    private val viewController: UIViewController,
) : SocialAuthenticator<AppleUser>, KoinComponent {

    /**
     * ASAuthorizationController의 Delegate는 기본적으로 Weak Reference로 저장됩니다.
     * KMP 환경에서는 Kotlin 객체가 Swift/Objective-C 객체의 Delegate로 지정될 때 예상보다 빨리 해제될 수 있으므로 이를 방지하기 위해
     * authorizationDelegate 클래스 변수로 유지하여 ARC에 의해 해제되지 않도록 Strong Reference로 유지합니다.
     * @author GunHyung-Ham
     * @since 2025.03.12
     */
    private var authorizationDelegate: ASAuthorizationControllerDelegateProtocol? = null

    override suspend fun authenticate(): SocialAuthResult<AppleUser> =
        suspendCoroutine { continuation ->
            val provider = ASAuthorizationAppleIDProvider()
            val request = provider.createRequest()
            request.requestedScopes =
                listOf(ASAuthorizationScopeFullName, ASAuthorizationScopeEmail)
            val controller = ASAuthorizationController(listOf(request))

            authorizationDelegate = object : NSObject(), ASAuthorizationControllerDelegateProtocol {

                @OptIn(BetaInteropApi::class)
                override fun authorizationController(
                    controller: ASAuthorizationController,
                    didCompleteWithAuthorization: ASAuthorization
                ) {
                    val credential = didCompleteWithAuthorization.credential as? ASAuthorizationAppleIDCredential
                    val idToken = credential?.identityToken?.let { data ->
                        NSString.create(data, NSUTF8StringEncoding)?.toString()
                    }

                    if (idToken.isNullOrEmpty()) {
                        continuation.resume(SocialAuthResult.Error)
                    } else {
                        continuation.resume(SocialAuthResult.Success(AppleUser(idToken)))
                    }

                    authorizationDelegate = null
                }

                override fun authorizationController(
                    controller: ASAuthorizationController,
                    didCompleteWithError: NSError
                ) {
                    val errorCode = didCompleteWithError.code.toInt()

                    when (errorCode) {
                        ASAuthorizationErrorCode.CANCELED.code -> {
                            continuation.resume(SocialAuthResult.UserCancelled)
                        }
                        else -> {
                            continuation.resume(SocialAuthResult.Error)
                        }
                    }

                    authorizationDelegate = null
                }
            }

            controller.delegate = authorizationDelegate
            controller.presentationContextProvider =
                object : NSObject(), ASAuthorizationControllerPresentationContextProvidingProtocol {
                    override fun presentationAnchorForAuthorizationController(controller: ASAuthorizationController): ASPresentationAnchor =
                        viewController.view.window
                }

            controller.performRequests()
        }

}
