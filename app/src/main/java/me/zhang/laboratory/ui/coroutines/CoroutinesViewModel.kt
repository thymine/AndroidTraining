package me.zhang.laboratory.ui.coroutines

import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import me.zhang.laboratory.App

class CoroutinesViewModel : ViewModel() {

    // Job and Dispatcher are combined into a CoroutineContext which
    // will be discussed shortly
    val scope = CoroutineScope(Job() + Dispatchers.Main)

    fun fetchNews() {
        scope.launch {
            for (i in 1..3) {
                val result = get("https://news.ycombinator.com")
                showOnMain(result)
                delay(3_000)
            }
        }
    }

    suspend fun fetchDocs() {                             // Dispatchers.Main
        delay(3_000)
        val result = get("https://developer.android.com") // Dispatchers.IO for `get`
        showOnMain(result)                                      // Dispatchers.Main
    }

    private fun showOnMain(result: String) {
        viewModelScope.launch(Dispatchers.Main) {
            Toast.makeText(App.context, result, Toast.LENGTH_LONG).show()
        }
    }

    private suspend fun get(url: String): String = withContext(Dispatchers.IO) {
        return@withContext url
    }

    override fun onCleared() {
        // Cancel the scope to cancel ongoing coroutines work
        scope.cancel()
        super.onCleared()
    }

}