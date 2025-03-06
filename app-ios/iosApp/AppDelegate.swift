//
//  AppDelegate.swift
//  iosApp
//
//  Created by 함건형 on 3/6/25.
//  Copyright © 2025 orgName. All rights reserved.
//
import UIKit
import Foundation
import KakaoSDKAuth
import KakaoSDKCommon
import Firebase
import FirebaseAnalytics
import App

class AppDelegate: UIResponder, UIApplicationDelegate {

    var window: UIWindow?

    func application(
            _ application: UIApplication,
            didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey: Any]?
        ) -> Bool {
            if let kakaoAppKey = Bundle.main.object(forInfoDictionaryKey: "KakaoNativeAppKey") as? String {
                KakaoSDK.initSDK(appKey: kakaoAppKey)
            } else {
                fatalError("❌ Kakao App Key is Not Found")
            }
            
            FirebaseApp.configure()
            CaramelAnalytics_iosKt.firebaseCallback(callback: FirebaseLoggingCallback())

            return true
        }
    
    func application(_ app: UIApplication, open url: URL, options: [UIApplication.OpenURLOptionsKey : Any] = [:]) -> Bool {
        if AuthApi.isKakaoTalkLoginUrl(url) {
            return AuthController.handleOpenUrl(url: url)
        }
      
        return false
    }
    
    class FirebaseLoggingCallback: IosAnalyticsCallback {

        func logEvent(eventId: String, params: String) {
            let dict = splitStringToDictionary(params, ",", ":")
            Analytics.logEvent(eventId, parameters: dict)
        }
    }

}
