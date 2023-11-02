plugins {
    alias(libs.plugins.android.application) version libs.versions.androidGradlePlugin apply false
    alias(libs.plugins.android.library) version libs.versions.androidGradlePlugin apply false
    alias(libs.plugins.kotlin.android) version libs.versions.kotlin apply false
    // Add the dependency for the Google services Gradle plugin
    alias(libs.plugins.google.services) version libs.versions.googleServicesPlugin apply false
    // Add the dependency for the Crashlytics Gradle plugin
    alias(libs.plugins.crashlytics) version libs.versions.crashlyticsPlugin apply false
}