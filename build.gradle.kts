extra.apply {
    set("compileSdkVersion", 33)
    set("buildToolsVersion", "33.0.1")
    set("ndkVersion", "25.1.8937393")

    set("minSdkVersion", 21)
    set("targetSdkVersion", 33)

    set("versionCode", 1)
    set("versionName", "1.0")

    set("kotlin_version", "1.7.20")
}

buildscript {
    repositories {
        mavenLocal()
        mavenCentral()
        maven {
            url = java.net.URI("https://android3.weizhipin.com/nexus/repository/public/")
        }
        maven { url = java.net.URI("https://jitpack.io") }
        google()
    }

    dependencies {
        classpath("com.android.tools.build:gradle:7.3.1")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.7.20")

        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

allprojects {
    repositories {
        mavenLocal()
        mavenCentral()
        maven {
            url = java.net.URI("https://android3.weizhipin.com/nexus/repository/public/")
        }
        google()
    }
}