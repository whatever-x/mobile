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
import FirebaseCore
import FirebaseMessaging
import App
import AppsFlyerLib
import AppTrackingTransparency
import AdSupport
import UserNotifications

class AppDelegate: UIResponder, UIApplicationDelegate {
    
    var window: UIWindow?
    var ConversionData: [AnyHashable: Any]? = nil
    var deferred_deep_link_processed_flag:Bool = false
    
    func application(
        _ application: UIApplication,
        didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey: Any]?
    ) -> Bool {

        FirebaseApp.configure()

        #if DEBUG
            Analytics.setAnalyticsCollectionEnabled(false)
            Crashlytics.crashlytics().setCrashlyticsCollectionEnabled(false)
        #endif
        
        Messaging.messaging().delegate = self

        UNUserNotificationCenter.current().delegate = self

        let authOptions: UNAuthorizationOptions = [.alert, .badge, .sound]
        UNUserNotificationCenter.current().requestAuthorization(
          options: authOptions,
          completionHandler: { _, _ in }
        )

        application.registerForRemoteNotifications()
        
        if let kakaoAppKey = Bundle.main.object(forInfoDictionaryKey: "KakaoNativeAppKey") as? String {
            KakaoSDK.initSDK(appKey: kakaoAppKey)
        } else {
            fatalError("❌ Kakao App Key is Not Found")
        }
        
        AppsFlyerLib.shared().isDebug = true
        if let appsFlyerKey = Bundle.main.object(forInfoDictionaryKey: "AppsFlyerKey") as? String {
            AppsFlyerLib.shared().appsFlyerDevKey = appsFlyerKey
        } else {
            fatalError("❌ Apps Flyer App Key is Not Found")
        }
        AppsFlyerLib.shared().appleAppID = "6745321351"
        AppsFlyerLib.shared().delegate = self
        AppsFlyerLib.shared().deepLinkDelegate = self
        AppsFlyerLib.shared().waitForATTUserAuthorization(timeoutInterval: 60)
        
        NotificationCenter.default.addObserver(
            self,
            selector: #selector(didBecomeActiveNotification),
            name: UIApplication.didBecomeActiveNotification,
            object: nil
        )
        
        return true
    }
    
    func application(
        _ application: UIApplication,
        didRegisterForRemoteNotificationsWithDeviceToken deviceToken: Data
    ) {
        Messaging.messaging().apnsToken = deviceToken
    }
    
    @objc func didBecomeActiveNotification() {
            AppsFlyerLib.shared().start()
        
            if #available(iOS 14, *) {
              ATTrackingManager.requestTrackingAuthorization { (status) in
                switch status {
                case .denied:
                    print("AuthorizationStatus is denied")
                case .notDetermined:
                    print("AuthorizationStatus is notDetermined")
                case .restricted:
                    print("AuthorizationStatus is restricted")
                case .authorized:
                    print("AuthorizationStatus is authorized")
                @unknown default:
                    fatalError("Invalid authorization status")
                }
              }
            }
        }

}

extension AppDelegate: DeepLinkDelegate {
    
    func didResolveDeepLink(_ result: DeepLinkResult) {
        switch result.status {
        case .notFound:
            NSLog("[AFSDK] Deep link not found")
            return
        case .failure:
            print("Error %@", result.error!)
            return
        case .found:
            guard let deepLinkObj:DeepLink = result.deepLink else {
                NSLog("[AFSDK] Could not extract deep link object")
                return
            }
            
            guard let deeplinkValue = deepLinkObj.deeplinkValue else { return }
            let params = deepLinkObj.clickEvent
            let parameterKeys = (1...10).map { "p\($0)" }
            var rawParams: [String: String?] = [:]

            for key in parameterKeys {
                rawParams[key] = params[key] as? String
            }
            let deepLinkHandler: DeepLinkHandler = GetDeepLinkHandler_iosKt.getDeepLinkHandler()
            
            deepLinkHandler.handleAppsFlyerDataRaw(
                deepLinkValue: deeplinkValue,
                rawParams: rawParams
            )
        }
    }
    
}

// TODO : 디퍼드 딥링크 추가 구현 필요
extension AppDelegate: AppsFlyerLibDelegate {
     
    func onConversionDataSuccess(_ data: [AnyHashable: Any]) {
        ConversionData = data
        
        if let conversionData = data as NSDictionary? as! [String:Any]? {
            
            if let status = conversionData["af_status"] as? String {
                if (status == "Non-organic") {
                    if let sourceID = conversionData["media_source"],
                       let campaign = conversionData["campaign"] {
                        NSLog("[AFSDK] This is a Non-Organic install. Media source: \(sourceID)  Campaign: \(campaign)")
                    }
                } else {
                    NSLog("[AFSDK] This is an organic install.")
                }
                
                if let is_first_launch = conversionData["is_first_launch"] as? Bool,
                   is_first_launch {
                    NSLog("[AFSDK] First Launch")
                    if (deferred_deep_link_processed_flag == true) {
                        NSLog("Deferred deep link was already processed by UDL. The DDL processing in GCD can be skipped.")
                        deferred_deep_link_processed_flag = false
                        return
                    }
                    
                    deferred_deep_link_processed_flag = true
                    
                    var deepLinkValue:String
                    
                    if conversionData.keys.contains("deep_link_value") {
                        deepLinkValue = conversionData["deep_link_value"] as! String
                    } else if conversionData.keys.contains("fruit_name") {
                        deepLinkValue = conversionData["fruit_name"] as! String
                    } else {
                        NSLog("Could not extract deep_link_value or fruit_name from deep link object using conversion data")
                        return
                    }
                    
                    NSLog("This is a deferred deep link opened using conversion data")
                    NSLog(deepLinkValue)
                } else {
                    NSLog("[AFSDK] Not First Launch")
                }
            }
        }
    }
    
    func onConversionDataFail(_ error: Error) {
        NSLog("[AFSDK] \(error)")
    }
}
