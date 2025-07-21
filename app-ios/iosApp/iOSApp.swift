import SwiftUI
import AppsFlyerLib
import KakaoSDKAuth

@main
struct iOSApp: App {

    @UIApplicationDelegateAdaptor(AppDelegate.self) var appDelegate
    @Environment(\.scenePhase) var scenePhase

	var body: some Scene {
		WindowGroup {
			ContentView()
                .onChange(of: scenePhase) { newPhase in
                    if newPhase == .active {
                        AppsFlyerLib.shared().start()
                    }
                }
                .onOpenURL { url in
                    AppsFlyerLib.shared().handleOpen(url, options: [:])

                    if (AuthApi.isKakaoTalkLoginUrl(url)) {
                        AuthController.handleOpenUrl(url: url)
                    }
                }
                .onContinueUserActivity(NSUserActivityTypeBrowsingWeb) { userActivity in
                    AppsFlyerLib.shared().continue(userActivity, restorationHandler: nil)
                }
		}
	}
}
