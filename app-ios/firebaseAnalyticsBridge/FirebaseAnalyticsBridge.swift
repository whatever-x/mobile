//
// Created by 함건형 on 2025. 10. 18..
// Copyright (c) 2025 orgName. All rights reserved.
//

import Foundation
import FirebaseCore
import FirebaseAnalytics

@objcMembers
public class FBAParam: NSObject {
    public let key: String
    public let value: Any?

    public init(key: String, value: Any?) {
        self.key = key
        self.value = value
        super.init()
    }
}

@objcMembers
public class FirebaseAnalyticsBridge: NSObject {

    public func logEvent(name: String, paramPairs: [FBAParam]?) {
        var dict: [String: Any] = [:]
        paramPairs?.forEach { p in
            dict[p.key] = p.value
        }
        let normalized = normalizeParams(dict)
        Analytics.logEvent(name, parameters: normalized)
    }

    /// User ID 설정
    public func setUserId(_ userId: String?) {
        Analytics.setUserID(userId)
    }

    /// Analytics 데이터 초기화
    public func resetAnalyticsData() {
        Analytics.resetAnalyticsData()
    }

    /// User Property 설정 (최대 25개, 24시간 캐싱될 수 있음)
    public func setUserProperty(_ value: String?, forName name: String) {
        Analytics.setUserProperty(value, forName: name)
    }

    /// 수집 on/off
    public func setAnalyticsCollectionEnabled(_ enabled: Bool) {
        Analytics.setAnalyticsCollectionEnabled(enabled)
    }

    /// 스크린 이름 수동 설정 (필요 시)
    public func logScreenView(screenName: String, screenClass: String? = nil) {
        var params: [String: Any] = [AnalyticsParameterScreenName: screenName]
        if let screenClass = screenClass {
            params[AnalyticsParameterScreenClass] = screenClass
        }
        Analytics.logEvent(AnalyticsEventScreenView, parameters: params)
    }

    // MARK: - Helpers

    /// Any → Obj-C 안전 타입(NSString/NSNumber)로 정규화
    private func normalizeParams(_ params: [String: Any]?) -> [String: Any]? {
        guard let params = params else { return nil }
        var dict: [String: Any] = [:]
        for (k, v) in params {
            dict[k] = normalizeValue(v)
        }
        return dict
    }

    private func normalizeValue(_ value: Any?) -> Any {
        guard let value else { return "null" } // Android와 유사한 폴백

        switch value {
        case let s as String:   return s as NSString
        case let b as Bool:     return NSNumber(value: b)
        case let i as Int:      return NSNumber(value: i)
        case let l as Int64:    return NSNumber(value: l)
        case let d as Double:   return NSNumber(value: d)
        case let f as Float:    return NSNumber(value: f)
        case let n as NSNumber: return n
            // 그 외 타입은 문자열로 폴백
        default:
            return String(describing: value) as NSString
        }
    }
}