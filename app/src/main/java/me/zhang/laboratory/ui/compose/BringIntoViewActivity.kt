package me.zhang.laboratory.ui.compose

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusTarget
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

class BringIntoViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BringPartOfComposableIntoViewSample()
        }
    }

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    fun BringIntoViewSample() {
        Row(Modifier.padding(6.dp).horizontalScroll(rememberScrollState())) {
            repeat(100) {
                val bringIntoViewRequester = remember { BringIntoViewRequester() }
                val coroutineScope = rememberCoroutineScope()
                Box(
                    Modifier
                        .size(100.dp)
                        .padding(6.dp)
                        .background(Color.Red)
                        // This associates the RelocationRequester with a Composable that wants to be
                        // brought into view.
                        .bringIntoViewRequester(bringIntoViewRequester)
                        .onFocusChanged {
                            if (it.isFocused) {
                                coroutineScope.launch {
                                    // This sends a request to all parents that asks them to scroll so
                                    // that this item is brought into view.
                                    bringIntoViewRequester.bringIntoView()
                                }
                            }
                        }
                        .focusTarget()
                )
            }
        }
    }

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    fun BringPartOfComposableIntoViewSample() {
        with(LocalDensity.current) {
            val bringIntoViewRequester = remember { BringIntoViewRequester() }
            val coroutineScope = rememberCoroutineScope()
            Column {
                Box(
                    Modifier
                        .border(2.dp, Color.Black)
                        .size(500f.toDp())
                        .horizontalScroll(rememberScrollState())
                ) {
                    Canvas(
                        Modifier
                            .size(1500f.toDp(), 500f.toDp())
                            // This associates the RelocationRequester with a Composable that wants
                            // to be brought into view.
                            .bringIntoViewRequester(bringIntoViewRequester)
                    ) {
                        drawCircle(color = Color.Red, radius = 250f, center = Offset(750f, 250f))
                    }
                }
                Button(
                    onClick = {
                        val circleCoordinates = Rect(500f, 0f, 1000f, 500f)
                        coroutineScope.launch {
                            // This sends a request to all parents that asks them to scroll so that
                            // the circle is brought into view.
                            bringIntoViewRequester.bringIntoView(circleCoordinates)
                        }
                    }
                ) {
                    Text("Bring circle into View")
                }
            }
        }
    }

    @Preview(showSystemUi = true)
    @Composable
    fun PreviewBringIntoView() {
        BringPartOfComposableIntoViewSample()
    }

}