plugins {
    id("com.google.devtools.ksp") version "1.9.10-1.0.13" apply false
}

extra.apply {
    set("compileSdkVersion", 34)
    set("minSdkVersion", 21)
    set("targetSdkVersion", 34)
    set("buildToolsVersion", "34.0.0")

    set("ndkVersion", "25.1.8937393")

    set("versionCode", 1)
    set("versionName", "1.0")

    set("kotlin_version", "1.9.10")

    set("compose_compiler_version", "1.5.3")

    set("retrofit2", "2.9.0")

    set("rxandroid", "3.0.2")
    set("rxjava", "3.1.8")
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
        classpath("com.android.tools.build:gradle:8.1.2")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.9.10")

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