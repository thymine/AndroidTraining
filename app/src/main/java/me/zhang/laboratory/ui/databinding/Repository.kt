package me.zhang.laboratory.ui.databinding

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

object Repository {

    suspend fun loadRepo(): Repo {
        return withContext(Dispatchers.IO) {
            delay(3000)
            Repo(
                "https://github.com/googlesamples/android-architecture-components",
                "Android Architecture Components. A collection of libraries that help you design robust, testable, and maintainable apps. Start with classes for managing your UI component lifecycle and handling data persistence. Provide a flexible, robust, and powerful architecture for building dynamic UI's. Android Architecture Components (AAc) is a collection of libraries that help you design robust, testable, and maintainable apps. AAc is a collection of"
            )
        }
    }

}