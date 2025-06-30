package com.whatever.caramel.core.firebaseMessaging

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import platform.Foundation.NSError

/**
 * only for iOS
 * @author GunHyung-Ham
 */
object FcmTokenReceiver {
    fun updateToken(
        token: String,
        completion: (NSError?) -> Unit,
    ) {
        val fcmTokenProvider: FcmTokenProvider = object : KoinComponent {}.get()

        CoroutineScope(Dispatchers.Main).launch {
            try {
                fcmTokenProvider.updateToken(token = token)
                completion(null)
            } catch (e: Throwable) {
                completion(
                    NSError(
                        domain = "FCMError",
                        code = -1,
                        userInfo = mapOf("message" to e.message),
                    ),
                )
            }
        }
    }
}
