package me.zhang.laboratory.ui.navi

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

class XActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { Main() }
    }

    @Composable
    fun Main() {
        Text(text = "Hello, Android!")
    }

    @Preview(showSystemUi = true)
    @Composable
    fun PreviewMain() {
        Main()
    }
}