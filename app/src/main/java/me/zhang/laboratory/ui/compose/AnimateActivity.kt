package me.zhang.laboratory.ui.compose

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.EnterExitState
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.animateColor
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FontDownload
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class AnimateActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { Main() }
    }

    @OptIn(ExperimentalAnimationApi::class)
    @Composable
    fun Main() {
        var visible by remember {
            mutableStateOf(false)
        }
        Column {
            val density = LocalDensity.current
            AnimatedVisibility(
                visible = visible,
                enter = slideInVertically { with(density) { -40.dp.roundToPx() } }
                        + expandVertically(expandFrom = Alignment.Top)
                        + fadeIn(initialAlpha = 0.3f),
                exit = slideOutVertically() + shrinkVertically() + fadeOut(),
            ) {
                val background by transition.animateColor(label = "background") { state ->
                    if (state == EnterExitState.Visible) Color.DarkGray else Color.LightGray
                }
                Text(
                    text = "AnimatedVisibility",
                    fontSize = 32.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(background)
                        .height(192.dp)
                        .animateEnterExit(
                            enter = slideInHorizontally(),
                            exit = slideOutHorizontally()
                        )
                )
            }

            Button(onClick = { visible = !visible }) {
                Text(text = "Click")
            }

            var count by remember {
                mutableIntStateOf(0)
            }
            AnimatedContent(
                targetState = count,
                transitionSpec = {
                    (fadeIn() + scaleIn()).togetherWith(fadeOut())
                },
                label = "Animated Count"
            ) { targetCount ->
                Text(text = "Count: $targetCount")
            }
            Button(onClick = { count++ }) {
                Text(text = "Add")
            }

            var isExpanded by remember {
                mutableStateOf(false)
            }
            AnimatedContent(
                targetState = isExpanded,
                transitionSpec = {
                    fadeIn(animationSpec = tween(150))
                        .togetherWith(fadeOut(animationSpec = tween(150))) using SizeTransform { initialSize, targetSize ->
                        if (targetState) {
                            keyframes {
                                IntSize(targetSize.width, initialSize.height) at 150
                                durationMillis = 400
                            }
                        } else {
                            keyframes {
                                IntSize(initialSize.width, targetSize.height) at 150
                                durationMillis = 400
                            }
                        }
                    }

                }, label = "Animated Text"
            ) { targetVisible ->
                if (targetVisible) {
                    Text(
                        text = "一大堆文本一大堆文本一大堆文本一大堆文本一大堆文本一大堆文本一大堆文本一大堆文本一大堆文本一大堆文本一大堆文本一大堆文本一大堆文本一大堆文本一大堆文本一大堆文本一大堆文本一大堆文本一大堆文本一大堆文本",
                        modifier = Modifier
                            .background(Color.Red)
                            .padding(16.dp)
                            .clickable {
                                isExpanded = false
                            })
                } else {
                    Icon(
                        imageVector = Icons.Outlined.FontDownload,
                        contentDescription = null,
                        modifier = Modifier
                            .background(Color.Red)
                            .padding(16.dp)
                            .clickable {
                                isExpanded = true
                            }
                    )
                }
            }

            var currentPage by remember {
                mutableStateOf("A")
            }
            Crossfade(targetState = currentPage, label = "Crossfade Page") { page ->
                Text(
                    text = page,
                    fontSize = 24.sp,
                    color = Color.White,
                    modifier = Modifier
                        .clickable {
                            currentPage = if (currentPage == "A") "B" else "A"
                        }
                        .background(Color.DarkGray)
                        .padding(32.dp)
                )
            }

            Box(
                modifier = Modifier
                    .background(Color.Gray)
                    .animateContentSize()
                    .clickable { isExpanded = !isExpanded }
            ) {
                Text(
                    text = "一大堆文本一大堆文本一大堆文本一大堆文本一大堆文本一大堆文本一大堆文本一大堆文本一大堆文本一大堆文本一大堆文本一大堆文本一大堆文本一大堆文本一大堆文本一大堆文本一大堆文本一大堆文本一大堆文本一大堆文本",
                    maxLines = if (isExpanded) Int.MAX_VALUE else 1,
                )
            }
        }
    }

    @Preview(showSystemUi = true)
    @Composable
    fun PreviewMain() {
        Main()
    }
}