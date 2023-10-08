plugins {
    id("com.google.devtools.ksp") version "1.8.10-1.0.9" apply false
}

extra.apply {
    set("compileSdkVersion", 34)
    set("minSdkVersion", 21)
    set("targetSdkVersion", 34)
    set("buildToolsVersion", "34.0.0")

    set("ndkVersion", "25.1.8937393")

    set("versionCode", 1)
    set("versionName", "1.0")

    set("kotlin_version", "1.9.0")

    set("retrofit2", "2.9.0")
}

buildscript {
    repositories {
        mavenLocal()
        mavenCentral()
        maven {
            url = java.net.URI("https://android3.weizhipin.com/nexus/repository/public/")
        }
        google()
    }

    dependencies {
        classpath("com.android.tools.build:gradle:8.1.1")
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