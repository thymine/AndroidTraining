package me.zhang.laboratory.ui.compose

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Chat
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

class LayoutActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { Main() }
    }

    @Composable
    fun Main() {
        Surface(
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            shadowElevation = 16.dp
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Jetpack Compose",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.W700
                )
                Spacer(modifier = Modifier.size(8.dp))
                Text(
                    text = "Row的horizontalArrangement参数帮助我们合理配置了按钮的水平位置。可以看到，喜欢和分享按钮呈左右两端对齐。",
                    style = MaterialTheme.typography.bodyLarge,
                )
                Spacer(modifier = Modifier.size(8.dp))
                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    IconButton(onClick = { }) {
                        Icon(imageVector = Icons.Rounded.Favorite, contentDescription = null)
                    }
                    IconButton(onClick = { }) {
                        Icon(imageVector = Icons.Rounded.Chat, contentDescription = null)
                    }
                    IconButton(onClick = { }) {
                        Icon(imageVector = Icons.Rounded.Share, contentDescription = null)
                    }
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