package me.zhang.laboratory.ui.coroutines

import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.launch

class CoroutinesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { Main() }
    }

    @Composable
    fun Main() {
        Box {
            Column {
                val viewModel by viewModels<CoroutinesViewModel>()
                Button(onClick = {
                    Toast.makeText(applicationContext, "Do fetching...", Toast.LENGTH_SHORT).show()
                    lifecycleScope.launch {
                        repeatOnLifecycle(Lifecycle.State.STARTED) {
                            viewModel.fetchDocs()
                        }
                    }
                }) {
                    Text("Fetch Docs")
                }
                Button(onClick = {
                    Toast.makeText(applicationContext, "Do fetching...", Toast.LENGTH_SHORT).show()
                    lifecycleScope.launch {
                        repeatOnLifecycle(Lifecycle.State.STARTED) {
                            viewModel.fetchNews()
                        }
                    }
                }) {
                    Text("Fetch News")
                }
                Button(onClick = {
                    Toast.makeText(applicationContext, "Do fetching...", Toast.LENGTH_SHORT).show()
                    lifecycleScope.launch {
                        repeatOnLifecycle(Lifecycle.State.STARTED) {
                            viewModel.fetchJokes()
                        }
                    }
                }) {
                    Text("Fetch Jokes")
                }
            }
        }
    }

    @Preview(showSystemUi = true)
    @Composable
    fun PreviewMain() {
        Main()
    }
}