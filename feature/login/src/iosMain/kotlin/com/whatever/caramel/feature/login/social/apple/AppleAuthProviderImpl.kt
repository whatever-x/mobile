package com.whatever.caramel.feature.login.social.apple

import androidx.compose.runtime.Composable
import androidx.compose.ui.uikit.LocalUIViewController
import com.whatever.caramel.feature.login.social.SocialAuthResult
import com.whatever.caramel.feature.login.social.SocialAuthenticator
import kotlinx.cinterop.BetaInteropApi
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
    private val viewController: UIViewController
) : SocialAuthenticator<AppleUser> {

    @OptIn(BetaInteropApi::class)
    override suspend fun authenticate(): SocialAuthResult<AppleUser> =
        suspendCoroutine { continuation ->
            val provider = ASAuthorizationAppleIDProvider()
            val request = provider.createRequest()
            request.requestedScopes = listOf(ASAuthorizationScopeFullName, ASAuthorizationScopeEmail)
            val controller = ASAuthorizationController(listOf(request))

            controller.delegate = object : NSObject(), ASAuthorizationControllerDelegateProtocol {
                override fun authorizationController(
                    controller: ASAuthorizationController,
                    didCompleteWithAuthorization: ASAuthorization
                ) {
                    val credential = didCompleteWithAuthorization.credential as? ASAuthorizationAppleIDCredential
                    val idToken = credential?.identityToken?.let { data ->
                        NSString.create(data, NSUTF8StringEncoding)?.toString()
                    }

                    continuation.resume(SocialAuthResult.Success(AppleUser(idToken = idToken!!)))
                }

                override fun authorizationController(
                    controller: ASAuthorizationController,
                    didCompleteWithError: NSError
                ) {
                    val errorCode = didCompleteWithError.code.toInt()
                    val socialAuthResult = when (errorCode) {
                        ASAuthorizationErrorCode.CANCELED.code -> SocialAuthResult.UserCancelled
                        else -> SocialAuthResult.Error
                    }

                    continuation.resume(socialAuthResult)
                }
            }

            controller.presentationContextProvider = object : NSObject(), ASAuthorizationControllerPresentationContextProvidingProtocol {
                override fun presentationAnchorForAuthorizationController(controller: ASAuthorizationController): ASPresentationAnchor =
                    viewController.view.window
            }

            controller.performRequests()
        }

}
