package me.zhang.laboratory.ui.compose

import android.content.res.Configuration
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import me.zhang.laboratory.ui.compose.theme.LabTheme
import me.zhang.laboratory.ui.compose.theme.caption
import me.zhang.laboratory.ui.compose.theme.h2

class ListActivity : AppCompatActivity() {

    private val localString = staticCompositionLocalOf { "Large" }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { Main() }
    }

    @Composable
    fun Main() {
        LabTheme {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(100, key = { index: Int -> index }) {
                    if (it % 2 == 0) {
                        CompositionLocalProvider(
                            localString provides "Medium"
                        ) {
                            Text(
                                text = "${localString.current} $it",
                                modifier = Modifier
                                    .background(Color.DarkGray)
                                    .padding(16.dp)
                                    .fillMaxWidth(),
                                style = me.zhang.laboratory.ui.compose.theme.h1,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    } else if (it % 3 == 0) {
                        val ls = compositionLocalOf { mutableStateOf("Lazy") }
                        Text(
                            text = "${ls.current.value} $it",
                            modifier = Modifier
                                .background(Color.Gray)
                                .padding(8.dp)
                                .fillMaxWidth(),
                            style = caption,
                            color = MaterialTheme.colorScheme.primary
                        )
                    } else {
                        Text(
                            text = "${localString.current} $it",
                            modifier = Modifier
                                .background(Color.LightGray)
                                .padding(32.dp)
                                .fillMaxWidth(),
                            style = h2,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
        }
    }

    @Preview(
        showSystemUi = true,
        uiMode = Configuration.UI_MODE_NIGHT_YES,
    )
    @Preview(
        showSystemUi = true,
        uiMode = Configuration.UI_MODE_NIGHT_NO,
    )
    @Composable
    fun PreviewMain() {
        LabTheme {
            Main()
        }
    }
}