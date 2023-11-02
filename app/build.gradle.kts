import java.io.FileInputStream
import java.util.Properties

plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-parcelize")
    alias(libs.plugins.ksp)
}

val keystorePropertiesFile = rootProject.file("app/keystore.properties")
val keystoreProperties = Properties()
keystoreProperties.load(FileInputStream(keystorePropertiesFile))

android {
    compileSdk = rootProject.extra["compileSdkVersion"] as Int
    buildToolsVersion = rootProject.extra["buildToolsVersion"] as String
    ndkVersion = rootProject.extra["ndkVersion"] as String

    namespace = "me.zhang.laboratory"

    defaultConfig {
        minSdk = rootProject.extra["minSdkVersion"] as Int
        targetSdk = rootProject.extra["targetSdkVersion"] as Int

        applicationId = "me.zhang.laboratory"
        versionCode = rootProject.ext["versionCode"] as Int
        versionName = rootProject.ext["versionName"] as String

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
            keyAlias = keystoreProperties["keyAlias"] as String
            keyPassword = keystoreProperties["keyPassword"] as String
            storeFile = file(keystoreProperties["storeFile"]!!)
            storePassword = keystoreProperties["storePassword"] as String
        }
    }

    buildTypes {
        getByName("release") {
            signingConfig = signingConfigs.getByName("debug")

            isMinifyEnabled = true
            proguardFiles("proguard-rules.pro")
        }

        // Build with ./gradlew app:installDebug -Pminify
        val minified = project.hasProperty("minify")
        getByName("debug") {
            signingConfig = signingConfigs.getByName("debug")

            isMinifyEnabled = minified
            proguardFiles("proguard-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    buildFeatures {
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

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    implementation(libs.lifecycle.extensions)
    testImplementation(libs.junit)

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
}