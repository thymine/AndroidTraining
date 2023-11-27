package me.zhang.laboratory.ui.compose

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalFoundationApi::class)
class PagerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { Main() }
    }

    @Composable
    fun Main() {
        Column {
            val horizontalPagerState = rememberPagerState(pageCount = { 10 })
            HorizontalPager(
                state = horizontalPagerState,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Gray)
                    .height(128.dp),
                verticalAlignment = Alignment.Bottom,
            ) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomEnd) {
                    Text(
                        text = "Page: $it",
                        modifier = Modifier
                            .background(Color.DarkGray)
                    )
                }
            }
            HorizontalDivider()
            val verticalPagerState = rememberPagerState(pageCount = { 10 })
            VerticalPager(
                state = verticalPagerState,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Gray)
                    .height(128.dp),
                horizontalAlignment = Alignment.End
            ) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.TopEnd) {
                    Text(
                        text = "Page: $it",
                        modifier = Modifier
                            .background(Color.DarkGray)
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