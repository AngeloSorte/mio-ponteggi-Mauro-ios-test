package com.example.platform

/**
 * Multiplatform abstraction interface for platform-specific capabilities.
 * In Android: opens Intent.ACTION_DIAL and geo map URI.
 * In iOS: opens tel:// and http://maps.apple.com via UIApplication.sharedApplication.
 */
interface PlatformActions {
    fun dialPhone(phoneNumber: String)
    fun openMap(latitude: Double, longitude: Double, label: String)
}

class DefaultPlatformActions(
    private val onDial: (String) -> Unit = {},
    private val onMap: (Double, Double, String) -> Unit = { _, _, _ -> }
) : PlatformActions {
    override fun dialPhone(phoneNumber: String) = onDial(phoneNumber)
    override fun openMap(latitude: Double, longitude: Double, label: String) = onMap(latitude, longitude, label)
}
