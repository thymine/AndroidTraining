package me.zhang.laboratory.ui.compose

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class ModifierActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BoxScreen()
        }
    }

    @Composable
    fun BoxScreen() {
        Box(
            modifier = Modifier
                .padding(8.dp)
                .border(2.dp, Color.Red, shape = RoundedCornerShape(2.dp))
                .padding(8.dp),
        ) {
            Spacer(
                modifier = Modifier
                    .size(128.dp, 48.dp)
                    .offset(10.dp, 15.dp)
                    .offset { IntOffset(40.dp.roundToPx(), 20.dp.roundToPx()) }
                    .fillMaxWidth()
                    .background(Color.Red)
            )

            Column {
                SelectionContainer {
                    Text(text = buildAnnotatedString {
                        withStyle(style = SpanStyle(fontSize = 24.sp)) {
                            append("Hello")
                        }
                        withStyle(
                            style = SpanStyle(
                                fontSize = 12.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = Color.LightGray,
                                textDecoration = TextDecoration.Underline,
                            )
                        ) {
                            append("World")
                        }
                        withStyle(style = SpanStyle(color = Color.Red)) {
                            append("!")
                        }
                        withStyle(style = ParagraphStyle(TextAlign.Center)) {
                            append("Another hello world.")
                        }
                    })
                }
                HorizontalDivider()
                var name by rememberSaveable {
                    mutableStateOf("")
                }
                TextField(
                    value = name,
                    onValueChange = { name = it },
                    modifier = Modifier.clip(shape = RoundedCornerShape(8.dp)),
                    label = {
                        Text(
                            text = "Name",
                            style = TextStyle(fontFamily = FontFamily.Cursive)
                        )
                    },
                    textStyle = TextStyle(fontFamily = FontFamily.Cursive),
                    colors = TextFieldDefaults.colors(
                        cursorColor = Color.Yellow,
                        focusedLabelColor = Color.Magenta,
                        unfocusedLabelColor = Color.Gray,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                    )
                )
                HorizontalDivider()
                Text(text = name)
            }
        }
    }

    @Composable
    fun HorizontalDivider() {
        Spacer(modifier = Modifier.height(16.dp))
    }

    @Composable
    @Preview(showBackground = true)
    fun BoxPreview() {
        BoxScreen()
    }
}