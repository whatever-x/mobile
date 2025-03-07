import UIKit
import SwiftUI
import App

struct ComposeView: UIViewControllerRepresentable {
    func makeUIViewController(context: Context) -> UIViewController {
        MainViewControllerKt.MainViewController()
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {}
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



