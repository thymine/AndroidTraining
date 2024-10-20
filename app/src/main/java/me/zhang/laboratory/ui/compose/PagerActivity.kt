package me.zhang.laboratory.ui.compose

import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.PagerSnapDistance
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import com.google.android.material.math.MathUtils.lerp
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue

private const val TAG = "PagerActivity"

@OptIn(ExperimentalFoundationApi::class)
class PagerActivity : AppCompatActivity() {

    private val twoPagesPerViewport = object : PageSize {
        override fun Density.calculateMainAxisPageSize(
            availableSpace: Int,
            pageSpacing: Int
        ): Int {
            return (availableSpace - 2 * pageSpacing) / 2
        }
    }

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
            val fling = PagerDefaults.flingBehavior(
                state = horizontalPagerState,
                pagerSnapDistance = PagerSnapDistance.atMost(10)
            )
            HorizontalPager(
//                beyondBoundsPageCount = 3,
                state = horizontalPagerState,
                contentPadding = PaddingValues(16.dp),
//                pageSize = PageSize.Fixed(256.dp)
                pageSize = twoPagesPerViewport,
                flingBehavior = fling,
            ) { page ->
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    Card(
                        Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .padding(end = 16.dp)
                            .graphicsLayer {
                                // Calculate the absolute offset for the current page from the
                                // scroll position. We use the absolute value which allows us to mirror
                                // any effects for both directions
                                val pageOffset = (
                                        (horizontalPagerState.currentPage - page) + horizontalPagerState
                                            .currentPageOffsetFraction
                                        ).absoluteValue

                                // We animate the alpha, between 50% and 100%
                                alpha = lerp(
                                    0.5f,
                                    1f,
                                    1f - pageOffset.coerceIn(0f, 1f)
                                )

//                                scaleX = lerp(
//                                    0.85f,
//                                    1f,
//                                    1f - pageOffset.coerceIn(0f, 1f)
//                                )
//
//                                scaleY = lerp(
//                                    0.85f,
//                                    1f,
//                                    1f - pageOffset.coerceIn(0f, 1f)
//                                )
                            }
                    ) {
                        // Card content
                        Text(
                            text = "Page: $page"
                        )
                    }
                }
            }

            HorizontalDivider()

            val verticalPagerState = rememberPagerState(pageCount = { 10 })
            LaunchedEffect(verticalPagerState) {
                // Collect from the a snapshotFlow reading the currentPage
                snapshotFlow { verticalPagerState.currentPage }.collect { currentPage ->
                    // Do something with each page change, for example:
                    // viewModel.sendPageSelectedEvent(page)
                    Log.d(TAG, "Page changed to $currentPage")
                }
            }
            Box {
                VerticalPager(
//                    beyondBoundsPageCount = 3,
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
                Column(
                    Modifier
                        .wrapContentSize()
                        .align(Alignment.CenterStart)
                        .padding(start = 8.dp),
                    verticalArrangement = Arrangement.Center
                ) {
                    repeat(verticalPagerState.pageCount) { iteration ->
                        val color =
                            if (verticalPagerState.currentPage == iteration) Color.DarkGray else Color.LightGray
                        Box(
                            modifier = Modifier
                                .padding(2.dp)
                                .clip(CircleShape)
                                .background(color)
                                .size(3.dp)
                        )
                    }
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