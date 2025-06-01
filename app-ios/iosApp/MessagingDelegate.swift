//
//  MessagingDelegate.swift
//  iosApp
//
//  Created by 함건형 on 6/1/25.
//  Copyright © 2025 orgName. All rights reserved.
//

import Foundation
import FirebaseMessaging
import App

extension AppDelegate: MessagingDelegate {

  func messaging(
    _ messaging: Messaging,
    didReceiveRegistrationToken fcmToken: String?
  ) {
    print("획득한 FCM token: \(String(describing: fcmToken))")
      guard let token = fcmToken else { return }
      FcmTokenProviderImpl.updateToken(token: token)
    // 여기서 얻은 토큰을 KMP 코드로 옮겨야함
    
    let dataDict: [String: String] = ["token": fcmToken ?? ""]
    NotificationCenter.default.post(
      name: Notification.Name("FCMToken"),
      object: nil,
      userInfo: dataDict
    )
  }

}
