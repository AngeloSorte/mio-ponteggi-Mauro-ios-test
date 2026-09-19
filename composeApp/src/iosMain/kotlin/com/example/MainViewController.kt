package com.example

import androidx.compose.ui.window.ComposeUIViewController
import com.example.platform.PlatformActions
import com.example.ui.UnipointAppContent
import com.example.ui.UnipointViewModel
import com.example.ui.theme.MyApplicationTheme
import platform.Foundation.NSURL
import platform.UIKit.UIApplication
import platform.UIKit.UIViewController

/**
 * iOS ViewController Entry point for Compose Multiplatform.
 * Used by SwiftUI (iosApp/ContentView.swift) via MainViewControllerKt.MainViewController().
 */
fun MainViewController(): UIViewController = ComposeUIViewController {
    val iosPlatformActions = object : PlatformActions {
        override fun dialPhone(phoneNumber: String) {
            val cleanNumber = phoneNumber.replace("/", "").replace(" ", "")
            val url = NSURL(string = "tel://$cleanNumber")
            if (UIApplication.sharedApplication.canOpenURL(url)) {
                UIApplication.sharedApplication.openURL(url)
            }
        }

        override fun openMap(latitude: Double, longitude: Double, label: String) {
            val encoded = label.replace(" ", "+")
            val url = NSURL(string = "http://maps.apple.com/?q=$encoded&ll=$latitude,$longitude")
            UIApplication.sharedApplication.openURL(url)
        }
    }

    val viewModel = UnipointViewModel()

    MyApplicationTheme {
        UnipointAppContent(
            viewModel = viewModel,
            platformActions = iosPlatformActions
        )
    }
}

/**
 * Overload accepting an external or pre-configured ViewModel instance.
 */
fun MainViewController(viewModel: UnipointViewModel): UIViewController = ComposeUIViewController {
    val iosPlatformActions = object : PlatformActions {
        override fun dialPhone(phoneNumber: String) {
            val cleanNumber = phoneNumber.replace("/", "").replace(" ", "")
            val url = NSURL(string = "tel://$cleanNumber")
            if (UIApplication.sharedApplication.canOpenURL(url)) {
                UIApplication.sharedApplication.openURL(url)
            }
        }

        override fun openMap(latitude: Double, longitude: Double, label: String) {
            val encoded = label.replace(" ", "+")
            val url = NSURL(string = "http://maps.apple.com/?q=$encoded&ll=$latitude,$longitude")
            UIApplication.sharedApplication.openURL(url)
        }
    }

    MyApplicationTheme {
        UnipointAppContent(
            viewModel = viewModel,
            platformActions = iosPlatformActions
        )
    }
}
