//
// Created by 함건형 on 2025. 7. 8..
// Copyright (c) 2025 orgName. All rights reserved.
//

import Foundation
import FirebaseCrashlytics

@objcMembers public class FBCrashlyticsBridge: NSObject {
    public func log(message : String) {
        Crashlytics.crashlytics().log(message)
    }

    public func recordException(errorTrace : String) {
        let error = NSError(
            domain : "KotlinException",
            code : 0,
            userInfo: [NSLocalizedDescriptionKey : errorTrace]
        )
        Crashlytics.crashlytics().record(error: error)
    }

    public func setKey(key : String, value : Any?) {
        let crashlytics = Crashlytics.crashlytics()
        guard let value = value else {
            crashlytics.setCustomValue("null", forKey: key)
            return
        }

        switch value{
        case let string as String:
            crashlytics.setCustomValue(string, forKey: key)

        case let number as NSNumber:
            crashlytics.setCustomValue(number, forKey: key)
        default :
            crashlytics.setCustomValue(String(describing: value), forKey: key)
        }
    }
}