pluginManagement {
    repositories {
        maven { url = uri("$rootProject.rootDir/.local") }
        mavenLocal()
        maven { url = uri("https://android3.weizhipin.com/nexus/repository/public/") }

        gradlePluginPortal()
        mavenCentral()
        google()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        maven { url = uri("$rootProject.rootDir/.local") }
        mavenLocal()
        maven { url = uri("https://android3.weizhipin.com/nexus/repository/public/") }

        mavenCentral()
        google()
    }
}

rootProject.name = "Laboratory"

include(":app")