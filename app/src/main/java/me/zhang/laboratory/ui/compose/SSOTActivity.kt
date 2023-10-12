package me.zhang.laboratory.ui.compose

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

class SSOTActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Screen()
        }
    }
}

@Composable
fun Screen() {
    var name by rememberSaveable { mutableStateOf("Zhang") }
    Content(name = name, onNameChanged = { name = it })
}

@Composable
fun Content(name: String, onNameChanged: (String) -> Unit) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Hello, $name!")
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(
            value = name,
            onValueChange = onNameChanged,
            label = { Text(text = "Input your name...") },
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = Color.Gray,
                focusedBorderColor = Color.Magenta
            ),
        )
    }
}

@Preview(showSystemUi = true)
@Composable
fun Preview() {
    Screen()
}