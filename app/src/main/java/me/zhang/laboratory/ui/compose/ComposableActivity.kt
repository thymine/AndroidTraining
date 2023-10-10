package me.zhang.laboratory.ui.compose

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.sp

class ComposableActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MessageCard("Hello Android")
        }
    }
}

@Composable
fun MessageCard(name: String) {
    Card(
        border = BorderStroke(
            Dp(1f),
            Color.Blue
        )
    ) {
        Text(
            modifier = Modifier
                .padding(Dp(12f))
                .background(Color.DarkGray)
                .border(Dp(1f), Color.Green),
            text = "Hello $name!",
            color = Color.White,
            fontSize = 24.sp,
        )
    }
}

@Preview
@Composable
fun PreviewMessageCard() {
    MessageCard("Android")
}