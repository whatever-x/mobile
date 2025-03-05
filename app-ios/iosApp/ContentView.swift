import UIKit
import SwiftUI
import Firebase
import FirebaseAnalytics
import App

struct ComposeView: UIViewControllerRepresentable {
    func makeUIViewController(context: Context) -> UIViewController {
        MainViewControllerKt.MainViewController()
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {}
}

class AppDelegate: NSObject, UIApplicationDelegate {
    func application(_ application: UIApplication,
                     didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey : Any]? = nil) -> Bool {
        FirebaseApp.configure()
    
        CaramelAnalytics_iosKt.firebaseCallback(callback: FirebaseLoggingCallback())
        return true
    }
}

class FirebaseLoggingCallback: IosAnalyticsCallback {

    func logEvent(eventId: String, params: String) {
        let dict = splitStringToDictionary(params, ",", ":")
        Analytics.logEvent(eventId, parameters: dict)
    }
}

func splitStringToDictionary(_ input: String, _ pairDelimiter: Character, _ keyValueDelimiter: Character) -> [String: String] {
    var result = [String: String]()

    let pairs = input.split(separator: pairDelimiter)

    for pair in pairs {
        let keyValueArray = pair.split(separator: keyValueDelimiter, maxSplits: 1).map { String($0) }
        if keyValueArray.count == 2 {
            let key = keyValueArray[0].trimmingCharacters(in: .whitespacesAndNewlines)
            let value = keyValueArray[1].trimmingCharacters(in: .whitespacesAndNewlines)
            result[key] = value
        }
    }

    return result
}

struct ContentView: View {
    var body: some View {
        ComposeView()
                .ignoresSafeArea(.keyboard)
                .ignoresSafeArea(.container)
    }
}



