import java.io.FileInputStream
import java.util.*

plugins {
    id("com.android.application")
    id("kotlin-android")
}

val keystorePropertiesFile = rootProject.file("app/keystore.properties")
val keystoreProperties = Properties()
keystoreProperties.load(FileInputStream(keystorePropertiesFile))

android {
    compileSdk = rootProject.extra["compileSdkVersion"] as Int
    buildToolsVersion = rootProject.extra["buildToolsVersion"] as String
    ndkVersion = rootProject.extra["ndkVersion"] as String

    defaultConfig {
        minSdk = rootProject.extra["minSdkVersion"] as Int
        targetSdk = rootProject.extra["targetSdkVersion"] as Int

        applicationId = "me.zhang.workbench"
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    buildFeatures {
        viewBinding = true
    }

    externalNativeBuild {
        ndkBuild {
            path = file("src/main/cpp/Android.mk")
        }
    }
    namespace = "me.zhang.laboratory"
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    implementation("androidx.lifecycle:lifecycle-extensions:2.2.0")
    testImplementation("junit:junit:4.13.2")

    implementation("androidx.appcompat:appcompat:1.5.1")
    implementation("androidx.recyclerview:recyclerview:1.2.1")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

    // https://github.com/google/flexbox-layout
    implementation("com.google.android.flexbox:flexbox:3.0.0")

    // https://github.com/google/gson
    implementation("com.google.code.gson:gson:2.9.1")

    implementation("com.google.android.material:material:1.7.0")

    // https://github.com/android/android-ktx
    implementation("androidx.core:core-ktx:1.9.0")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7:${rootProject.extra["kotlin_version"]}")

    val activityVersion = "1.6.1"

    // Java language implementation
    implementation("androidx.activity:activity:$activityVersion")
    // Kotlin
    implementation("androidx.activity:activity-ktx:$activityVersion")

    val fragmentVersion = "1.5.4"

    // Java language implementation
    implementation("androidx.fragment:fragment:$fragmentVersion")
    // Kotlin
    implementation("androidx.fragment:fragment-ktx:$fragmentVersion")
    // Testing Fragments in Isolation
    debugImplementation("androidx.fragment:fragment-testing:$fragmentVersion")

    // https://github.com/google/dagger
    val daggerVersion = "2.42"
    implementation("com.google.dagger:dagger:$daggerVersion")
    annotationProcessor("com.google.dagger:dagger-compiler:$daggerVersion")
    implementation("com.google.dagger:dagger-android:$daggerVersion")
    implementation("com.google.dagger:dagger-android-support:$daggerVersion")
    // if you use the support libraries
    annotationProcessor("com.google.dagger:dagger-android-processor:$daggerVersion")

    // https://github.com/google/guava
    implementation("com.google.guava:guava:31.1-android")
}