//
//  NotificationCenterDelegate.swift
//  iosApp
//
//  Created by 함건형 on 6/1/25.
//  Copyright © 2025 orgName. All rights reserved.
//

import Foundation
import UserNotifications

extension AppDelegate: UNUserNotificationCenterDelegate {

    func userNotificationCenter(
        _ center: UNUserNotificationCenter,
        willPresent notification: UNNotification
    ) async -> UNNotificationPresentationOptions {
        let title = notification.request.content.title
        let body = notification.request.content.body
        print("📬 수신된 메시지 (포그라운드):", title, body)
        
        return [[.banner, .sound, .badge]] // iOS 15+
    }

    func userNotificationCenter(
        _ center: UNUserNotificationCenter,
        didReceive response: UNNotificationResponse
    ) async {
        let title = response.notification.request.content.title
        let body = response.notification.request.content.body
        print("📬 사용자 클릭 메시지:", title, body)
    }
    
}
