package com.example

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.example.platform.PlatformActions
import com.example.ui.UnipointAppContent
import com.example.ui.UnipointViewModel
import com.example.ui.theme.MyApplicationTheme

/**
 * Android Entry point.
 * Hosts the pure Compose Multiplatform [UnipointAppContent].
 */
class MainActivity : ComponentActivity() {

    private val viewModel: UnipointViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val androidPlatformActions = object : PlatformActions {
            override fun dialPhone(phoneNumber: String) {
                try {
                    val cleanNumber = phoneNumber.replace("/", "").replace(" ", "")
                    val intent = Intent(Intent.ACTION_DIAL).apply {
                        data = Uri.parse("tel:$cleanNumber")
                    }
                    startActivity(intent)
                } catch (_: Exception) {
                    // Safe fallback
                }
            }

            override fun openMap(latitude: Double, longitude: Double, label: String) {
                try {
                    val uri = Uri.parse("geo:$latitude,$longitude?q=${Uri.encode(label)}")
                    val intent = Intent(Intent.ACTION_VIEW, uri)
                    startActivity(intent)
                } catch (_: Exception) {
                    // Safe fallback
                }
            }
        }

        setContent {
            MyApplicationTheme {
                UnipointAppContent(
                    viewModel = viewModel,
                    platformActions = androidPlatformActions
                )
            }
        }
    }
}
