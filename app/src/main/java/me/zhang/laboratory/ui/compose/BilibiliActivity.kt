package me.zhang.laboratory.ui.compose

import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Done
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Slider
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TriStateCheckbox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.state.ToggleableState
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

class BilibiliActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreen()
        }
    }

    @Composable
    private fun MainScreen() {
        Box(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize()
        ) {
            SearchBox()
            Column {
                Cb(Modifier.padding(vertical = 78.dp))
                S()
                Progress()
            }
            Fab(Modifier.align(Alignment.BottomEnd))
        }
    }

    @Composable
    fun Progress() {
        var progress by rememberSaveable {
            mutableFloatStateOf(0.1f)
        }
        val animatedProgress by animateFloatAsState(
            targetValue = progress,
            animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec,
            label = ""
        )
        CircularProgressIndicator(
            progress = animatedProgress
        )
        LinearProgressIndicator(
            progress = animatedProgress
        )
        Button(onClick = {
            progress += 0.1f
        }) {
            Text(text = "增加进度")
        }
    }

    @Composable
    private fun S() {
        Column {
            var sliderValue by rememberSaveable {
                mutableFloatStateOf(0.5f)
            }
            Text(text = "音量：${(sliderValue * 100).toInt()}%")
            Slider(value = sliderValue, onValueChange = { sliderValue = it })
        }
    }

    @Composable
    private fun Cb(modifier: Modifier) {
        Column(modifier = modifier) {
            var checkState by rememberSaveable { mutableStateOf(false) }
            Checkbox(checked = checkState, onCheckedChange = { checkState = it })
            HorizontalDivider()
            TriStateCb()
            HorizontalDivider()
            Switch(checked = checkState, onCheckedChange = { checkState = it })

            if (checkState) {
                DialogScreen()
            }
        }
    }

    @Composable
    private fun DialogScreen() {
        var openDialog by rememberSaveable {
            mutableStateOf(true)
        }
        if (openDialog) {
            Dialog(onDismissRequest = {
                Toast.makeText(
                    applicationContext,
                    "Dialog dismissed!",
                    Toast.LENGTH_LONG
                ).show()
                openDialog = false
            }) {
                Box(
                    modifier = Modifier
                        .background(Color.White, CircleShape)
                        .padding(16.dp),
                ) {
                    Text(
                        text = "Hello Compose Dialog!",
                        color = Color.Blue,
                        modifier = Modifier
                            .align(Alignment.Center),
                    )
                }
            }

            AlertDialog(
                onDismissRequest = {
                    openDialog = false
                },
                confirmButton = {
                    Button(onClick = { openDialog = false }) {
                        Text(
                            text = "Confirm",
                            color = Color.White,
                        )
                    }
                },
                title = {
                    Text(
                        text = "Hello",
                        color = Color.Blue,
                    )
                },
                text = {
                    Text(
                        text = "Compose Dialog!",
                        color = Color.Blue,
                    )
                },
                properties = DialogProperties(
                    dismissOnBackPress = false,
                    dismissOnClickOutside = false
                )
            )
        }
    }

    @Composable
    private fun TriStateCb() {
        Column {
            val (checkState0, onCheckedChange0) = rememberSaveable {
                mutableStateOf(false)
            }
            val (checkState1, onCheckedChange1) = rememberSaveable {
                mutableStateOf(true)
            }
            val parentState = rememberSaveable(checkState0, checkState1) {
                if (checkState0 && checkState1) ToggleableState.On
                else if (!checkState0 && !checkState1) ToggleableState.Off
                else ToggleableState.Indeterminate
            }
            val onParentClick = {
                val s = parentState != ToggleableState.On
                onCheckedChange0(s)
                onCheckedChange1(s)
            }
            TriStateCheckbox(
                state = parentState, onClick = onParentClick,
                colors = CheckboxDefaults.colors(checkedColor = Color.Red)
            )
            LabeledCheckbox(
                label = "USA",
                modifier = Modifier.padding(start = 8.dp),
                checked = checkState0,
                onCheckedChange = onCheckedChange0,
                colors = CheckboxDefaults.colors(checkedColor = Color.Red)
            )
            LabeledCheckbox(
                label = "CHINA",
                modifier = Modifier.padding(start = 8.dp),
                checked = checkState1,
                onCheckedChange = onCheckedChange1,
                colors = CheckboxDefaults.colors(checkedColor = Color.Red)
            )
        }
    }

    @Composable
    private fun Fab(modifier: Modifier) {
        Column(
            modifier = modifier,
            horizontalAlignment = Alignment.End,
        ) {
            FloatingActionButton(
                onClick = { },
                shape = CircleShape
            ) {
                Icon(imageVector = Icons.Rounded.Add, contentDescription = null)
            }
            HorizontalDivider()
            ExtendedFloatingActionButton(
                onClick = { },
                shape = RoundedCornerShape(16.dp)
            ) {
                Icon(imageVector = Icons.Rounded.Favorite, contentDescription = null)
                VerticalDivider()
                Text(text = "FAVORS", fontSize = 16.sp)
            }
        }
    }

    @Composable
    private fun SearchBox() {
        var query by rememberSaveable {
            mutableStateOf("")
        }
        Row {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White, CircleShape)
                    .padding(horizontal = 16.dp)
                    .weight(1f),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Rounded.Search,
                    contentDescription = null,
                    tint = Color.Gray
                )

                val fontSize = 18.sp
                TextField(
                    value = query,
                    onValueChange = {
                        query = it
                    },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                    ),
                    modifier = Modifier
                        .weight(1f)
                        .height(64.dp),
                    placeholder = {
                        Text(
                            text = "Try to input something~",
                            fontFamily = FontFamily.Cursive,
                            fontSize = fontSize,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                        )
                    },
                    singleLine = true,
                    textStyle = TextStyle(fontFamily = FontFamily.Serif, fontSize = fontSize)
                )
                if (query.isNotBlank()) {
                    IconButton(onClick = { query = "" }) {
                        Icon(
                            imageVector = Icons.Rounded.Close,
                            contentDescription = null,
                            tint = Color.Gray
                        )
                    }
                }
            }
            VerticalDivider()

            val interactionSource = remember {
                MutableInteractionSource()
            }
            val pressedAsState = interactionSource.collectIsPressedAsState()
            val borderColor = if (pressedAsState.value) Color.Green else Color.Transparent
            Button(
                onClick = {
                    Toast.makeText(
                        applicationContext,
                        "Search \"$query\"",
                        Toast.LENGTH_LONG
                    ).show()
                },
                modifier = Modifier
                    .align(Alignment.CenterVertically),
                enabled = query.isNotBlank(),
                border = BorderStroke(1.dp, borderColor),
                interactionSource = interactionSource
            ) {
                Icon(
                    imageVector = Icons.Rounded.Done,
                    contentDescription = null,
                )
            }
        }
    }

    @Preview(showBackground = true, showSystemUi = true)
    @Composable
    private fun PreviewMain() {
        MainScreen()
    }
}