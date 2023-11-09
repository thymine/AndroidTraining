package me.zhang.laboratory.ui.coroutines

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import me.zhang.laboratory.App

private const val TAG = "CoroutinesViewModel"

class CoroutinesViewModel : ViewModel() {

    // Job and Dispatcher are combined into a CoroutineContext which
    // will be discussed shortly
    private val scope = CoroutineScope(Job() + Dispatchers.Main)

    fun fetchNews() {
        scope.launch {
            for (i in 1..3) {
                val result = get("https://news.ycombinator.com")
                showOnMain(result)
                delay(1_000)
            }
        }
    }

    fun fetchJokes() {
        // Starts a new coroutine on Dispatchers.Main as it's the scope's default
        val job1 = scope.launch {
            // New coroutine with CoroutineName = "coroutine" (default)
            Log.d(TAG, "fetchJokes: coroutine")
            for (i in 1..3) {
                val result = get("https://icanhazdadjoke.com")
                showOnMain("coroutine: $result")
                delay(1_000)
            }
        }
        job1.invokeOnCompletion {
            val res = "fetchJokes: job1 completed"
            Log.d(TAG, res)

            showOnMain(res)
        }

        // Starts a new coroutine on Dispatchers.Default
        val job2 = scope.launch(Dispatchers.Default + CoroutineName("BackgroundCoroutine")) {
            // New coroutine with CoroutineName = "BackgroundCoroutine" (overridden)
            Log.d(TAG, "fetchJokes: BackgroundCoroutine")
            for (i in 1..3) {
                val result = get("https://icanhazdadjoke.com")
                showOnMain("BackgroundCoroutine: $result")
                delay(1_000)
            }
        }
        job2.invokeOnCompletion {
            val res = "fetchJokes: job2 completed"
            Log.d(TAG, res)

            showOnMain(res)
        }
    }

    suspend fun fetchDocs() {                             // Dispatchers.Main
        delay(1_000)
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