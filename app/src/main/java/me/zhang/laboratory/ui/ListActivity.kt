package me.zhang.laboratory.ui

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

class ListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { Main() }
    }

    @Composable
    fun Main() {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.LightGray),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(100, key = { index: Int -> index }) {
                if (it % 2 == 0) {
                    Text(
                        text = "Lazy $it",
                        modifier = Modifier
                            .background(Color.DarkGray)
                            .padding(16.dp)
                            .fillMaxWidth()
                    )
                } else {
                    Text(
                        text = "Lazy $it",
                        modifier = Modifier
                            .background(Color.Gray)
                            .padding(32.dp)
                            .fillMaxWidth()
                    )
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