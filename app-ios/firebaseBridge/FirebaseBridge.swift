import Foundation
import FirebaseMessaging

@objcMembers public class FcmTokenBridge: NSObject {
    public func requestToken(
        completion: @escaping (String?) -> Void
    ) {
        Messaging.messaging().token { token, error in
            if let token = token {
                print("✅ FCM 토큰 획득: \(token)")
                completion(token)
            } else {
                print("❌ FCM 토큰 실패: \(error?.localizedDescription ?? "Unknown error")")
                completion(nil)
            }
        }
    }
}