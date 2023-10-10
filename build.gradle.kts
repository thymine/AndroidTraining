extra.apply {
    set("compileSdkVersion", 34)
    set("buildToolsVersion", "34.0.0")
    set("ndkVersion", "25.1.8937393")

    set("minSdkVersion", 21)
    set("targetSdkVersion", 34)

    set("versionCode", 1)
    set("versionName", "1.0")
}

buildscript {
    repositories {
        mavenLocal()
        maven {
            url = uri("https://android3.weizhipin.com/nexus/repository/public/")
        }
        mavenCentral()
        google()
    }

    dependencies {
        classpath(libs.android.gradlePlugin)
        classpath(libs.kotlin.gradlePlugin)

        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

allprojects {
    repositories {
        mavenLocal()
        maven {
            url = uri("https://android3.weizhipin.com/nexus/repository/public/")
        }
        mavenCentral()
        google()
    }
}