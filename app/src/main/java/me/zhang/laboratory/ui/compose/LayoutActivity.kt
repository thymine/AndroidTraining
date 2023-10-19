package me.zhang.laboratory.ui.compose

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.AlignmentLine
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.FirstBaseline
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.layout
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import me.zhang.laboratory.R

class LayoutActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { Main() }
    }

    @Composable
    fun Main() {
        Column {
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
            Box {
                Box(
                    modifier = Modifier
                        .background(Color.Red)
                        .size(240.dp)
                ) {

                }
                Box(
                    modifier = Modifier
                        .background(Color.Green)
                        .size(120.dp)
                ) {

                }
                Text(text = "Hello World")
            }
            Surface(
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp),
                shadowElevation = 16.dp
            ) {
                Row {
                    Image(
                        painter = painterResource(id = R.drawable.scenery),
                        contentDescription = null,
                        modifier = Modifier
                            .size(100.dp),
                        contentScale = ContentScale.Crop
                    )
                    VerticalDivider()
                    Column(
                        modifier = Modifier
                            .fillMaxHeight(),
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(text = "Jetpack", style = MaterialTheme.typography.titleLarge)
                        HorizontalDivider()
                        Text(text = "Compose", style = MaterialTheme.typography.bodySmall)
                    }
                }
            }
            CustomLayout(modifier = Modifier.padding(16.dp)) {
                Text(text = "One")
                Text(text = "Two")
                Text(text = "Three")
                Text(
                    text = "Four",
                    modifier = Modifier
                        .background(Color.Red)
                        .firstBaselineToTop(24.dp)
                )
                Text(
                    text = "Five",
                    Modifier
                        .background(Color.Green)
                        .padding(top = 24.dp)
                )
            }
        }
    }

    @Composable
    fun CustomLayout(modifier: Modifier = Modifier, content: @Composable () -> Unit) {
        Layout(
            content = content,
            modifier = modifier
        ) { measurables, constraints ->
            val placeables = measurables.map { measurable ->
                measurable.measure(constraints)
            }
            var yPosition = 0;
            layout(constraints.maxWidth, constraints.maxHeight) {
                placeables.forEach { placeable ->
                    placeable.placeRelative(0, yPosition)
                    yPosition += placeable.height
                }
            }
        }
    }

    private fun Modifier.firstBaselineToTop(
        firstBaselineToTop: Dp
    ) = this then Modifier.layout { measurable, constraints ->
        val placeable = measurable.measure(constraints)
        val firstBaseline = placeable[FirstBaseline]
        check(firstBaseline != AlignmentLine.Unspecified)

        val placeableY = firstBaselineToTop.roundToPx() - firstBaseline
        val height = placeable.height + placeableY
        layout(placeable.width, height) {
            placeable.placeRelative(0, placeableY)
        }
    }

    @Preview(showSystemUi = true)
    @Composable
    fun PreviewMain() {
        Main()
    }
}
