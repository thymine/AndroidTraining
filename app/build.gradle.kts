import com.google.firebase.crashlytics.buildtools.gradle.CrashlyticsExtension
import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.ksp)
    alias(libs.plugins.kotlin.android)
    id("kotlin-parcelize")
    id("org.jetbrains.kotlin.kapt")
    alias(libs.plugins.safeargs.kotlin)
    // Add the Google services Gradle plugin
    alias(libs.plugins.google.services)
    // Add the Crashlytics Gradle plugin
    alias(libs.plugins.crashlytics)
}

android {
    compileSdk = libs.versions.compileSdk.get().toInt()
    buildToolsVersion = libs.versions.buildTools.get()
    ndkVersion = libs.versions.ndk.get()

    namespace = "me.zhang.laboratory"

    defaultConfig {
        applicationId = "me.zhang.laboratory"

        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()

        versionCode = libs.versions.versionCode.get().toInt()
        versionName = libs.versions.versionName.get()

        ndk {
            /* 只需要64位的ABI */
            // abiFilters.add("armeabi-v7a")
            abiFilters.add("arm64-v8a")
            // abiFilters.add("x86")
            abiFilters.add("x86_64")
        }
    }

    signingConfigs {
        getByName("debug") {
            val properties = getSigningConfigs("debug.properties")

            keyAlias = properties["keyAlias"] as String
            keyPassword = properties["keyPassword"] as String
            storeFile = file(properties["storeFile"]!!)
            storePassword = properties["storePassword"] as String
        }
        create("release") {
            val properties = getSigningConfigs("release.properties")

            keyAlias = properties["keyAlias"] as String
            keyPassword = properties["keyPassword"] as String
            storeFile = file(properties["storeFile"]!!)
            storePassword = properties["storePassword"] as String
        }
    }

    buildTypes {
        getByName("release") {
            signingConfig = signingConfigs.getByName("release")

            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )

            configure<CrashlyticsExtension> {
                mappingFileUploadEnabled = true
            }
        }

        // Build with ./gradlew app:installDebug -Pminify
        val minified = project.hasProperty("minify")
        getByName("debug") {
            signingConfig = signingConfigs.getByName("debug")

            isMinifyEnabled = minified
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )

            configure<CrashlyticsExtension> {
                mappingFileUploadEnabled = false
            }
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    buildFeatures {
        dataBinding = true
        viewBinding = true
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.composeCompilerVersion.get()
    }

    externalNativeBuild {
        ndkBuild {
            path = file("src/main/cpp/Android.mk")
        }
    }
}

fun getSigningConfigs(path: String): Properties {
    return Properties().apply {
        load(FileInputStream(project.file(path)))
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    implementation(project(":feature:sample"))
    implementation(project(":feature:topic"))
    implementation(project(":feature:foryou"))

    implementation(libs.lifecycle.extensions)
    implementation(libs.firebase.messaging.ktx)

    testImplementation(libs.junit)
    testImplementation(libs.kotlinx.coroutines.test)

    implementation(libs.appcompat)
    implementation(libs.recyclerview)
    implementation(libs.constraintlayout)

    // https://github.com/google/flexbox-layout
    implementation(libs.flexbox)

    // https://github.com/google/gson
    implementation(libs.gson)

    implementation(libs.material)

    // https://github.com/android/android-ktx
    implementation(libs.core.ktx)
    implementation(libs.kotlin.stdlib.jdk7)

    // Java language implementation
    implementation(libs.activity)
    // Kotlin
    implementation(libs.activity.ktx)

    // Java language implementation
    implementation(libs.fragment)
    // Kotlin
    implementation(libs.fragment.ktx)
    // Testing Fragments in Isolation
    debugImplementation(libs.fragment.testing)

    // https://github.com/google/dagger
    implementation(libs.dagger)
    annotationProcessor(libs.dagger.compiler)
    implementation(libs.dagger.android)
    implementation(libs.dagger.android.support)
    // if you use the support libraries
    annotationProcessor(libs.dagger.android.processor)

    // https://github.com/google/guava
    implementation(libs.guava)

    implementation(libs.room.runtime)
    annotationProcessor(libs.room.compiler)

    // To use Kotlin Symbol Processing (KSP)
    ksp(libs.room.compiler)

    // optional - Kotlin Extensions and Coroutines support for Room
    implementation(libs.room.ktx)

    // https://github.com/square/retrofit
    implementation(libs.retrofit2)
    implementation(libs.converter.gson)

    implementation(libs.rxandroid)
    // Because RxAndroid releases are few and far between, it is recommended you also
    // explicitly depend on RxJava's latest version for bug fixes and new features.
    // (see https://github.com/ReactiveX/RxJava/releases for latest 3.x.x version)
    implementation(libs.rxjava)

    ksp(libs.ksp.glide)
    implementation(libs.glide)

    //region Compose
    val composeBom = platform(libs.compose.bom)
    implementation(composeBom)
    androidTestImplementation(composeBom)

    // Choose one of the following:
    // Material Design 3
    implementation(libs.compose.material3)
//    // or Material Design 2
//    implementation(libs.compose.material)
//    // or skip Material Design and build directly on top of foundational components
//    implementation(libs.compose.foundation)
//    // or only import the main APIs for the underlying toolkit systems,
//    // such as input and measurement/layout
//    implementation(libs.compose.ui)

    // Android Studio Preview support
    implementation(libs.compose.ui.tooling.preview)
    debugImplementation(libs.compose.ui.tooling)

    // UI Tests
    androidTestImplementation(libs.compose.ui.test.junit4)
    debugImplementation(libs.compose.ui.test.manifest)

    // Optional - Included automatically by material, only add when you need
    // the icons but not the material library (e.g. when using Material3 or a
    // custom design system based on Foundation)
    implementation(libs.compose.material.icons.core)
    // Optional - Add full set of material icons
    implementation(libs.compose.material.icons.extended)
    // Optional - Add window size utils
    implementation(libs.compose.material3.window.size.clazz)

    // Optional - Integration with activities
    implementation(libs.activity.compose)
    // Optional - Integration with ViewModels
    implementation(libs.lifecycle.viewmodel.compose)
    // Optional - Integration with LiveData
    implementation(libs.compose.runtime.livedata)
    // Optional - Integration with RxJava
    implementation(libs.compose.runtime.rxjava3)

    implementation(libs.compose.constraintlayout)
    //endregion

    // Import the Firebase BoM
    implementation(platform(libs.firebase.bom))
    // When using the BoM, don't specify versions in Firebase dependencies
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.crashlytics)
    // Add the dependencies for any other desired Firebase products
    // https://firebase.google.com/docs/android/setup#available-libraries

    // Add the dependency for the Firebase Authentication library
    // When using the BoM, you don't specify versions in Firebase library dependencies
    implementation(libs.firebase.auth)

    implementation(libs.firebase.ui.auth)

    // Also add the dependency for the Google Play services library and specify its version
    implementation(libs.play.services.auth)

    //region Navigation
    // Java language implementation
    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)

    // Kotlin
    implementation(libs.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)

    // Feature module Support
    implementation(libs.androidx.navigation.dynamic.features.fragment)

    // Testing Navigation
    androidTestImplementation(libs.androidx.navigation.testing)

    // Jetpack Compose Integration
    implementation(libs.androidx.navigation.compose)
    //endregion
}