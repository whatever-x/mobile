import SwiftUI
import AppsFlyerLib

@main
struct iOSApp: App {

    @UIApplicationDelegateAdaptor(AppDelegate.self) var appDelegate

	var body: some Scene {
		WindowGroup {
			ContentView()
                .onOpenURL { url in
                    AppsFlyerLib.shared().handleOpen(url, options: [:])
                }
		}
	}
}
