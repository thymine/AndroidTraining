package me.zhang.laboratory.ui.compose

import android.os.Bundle
import android.util.Log
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
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

private const val TAG = "PagerActivity"

@OptIn(ExperimentalFoundationApi::class)
class PagerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { Main() }
    }

    @Composable
    fun Main() {
        Column {
            val horizontalPagerState = rememberPagerState(pageCount = { 10_000 })
            LaunchedEffect(horizontalPagerState) {
                snapshotFlow { horizontalPagerState.settledPage }.collect { settledPage ->
                    Log.d(TAG, "Settled page: $settledPage")
                }
            }
            HorizontalPager(
                beyondBoundsPageCount = 3,
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

            val verticalPagerState = rememberPagerState(pageCount = { 10_000 })
            LaunchedEffect(verticalPagerState) {
                // Collect from the a snapshotFlow reading the currentPage
                snapshotFlow { verticalPagerState.currentPage }.collect { currentPage ->
                    // Do something with each page change, for example:
                    // viewModel.sendPageSelectedEvent(page)
                    Log.d(TAG, "Page changed to $currentPage")
                }
            }
            VerticalPager(
                beyondBoundsPageCount = 3,
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

            HorizontalDivider()

            // scroll to page
            val coroutineScope = rememberCoroutineScope()
            Button(onClick = {
                coroutineScope.launch {
                    // Call scroll to on pagerState
                    horizontalPagerState.animateScrollToPage(5)
                    verticalPagerState.scrollToPage(5)
                }
            }) {
                Text("Jump to Page 5")
            }
        }
    }

    @Preview(showSystemUi = true)
    @Composable
    fun PreviewMain() {
        Main()
    }
}