package me.zhang.laboratory.ui.compose

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
class DatePickerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Content()
        }
    }

    @Composable
    private fun Content() {
        DarkLightMaterialTheme {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                val state = rememberDatePickerState(initialDisplayMode = DisplayMode.Input)
                DatePicker(state = state, modifier = Modifier.padding(16.dp))

                Text("Entered date timestamp: ${state.selectedDateMillis ?: "no input"}")
            }
        }
    }

    @Preview(showSystemUi = true)
    @Composable
    fun PreviewContent() {
        Content()
    }

}