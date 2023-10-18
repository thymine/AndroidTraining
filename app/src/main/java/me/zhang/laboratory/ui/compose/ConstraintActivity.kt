package me.zhang.laboratory.ui.compose

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import me.zhang.laboratory.R

class ConstraintActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { Main() }
    }

    @Composable
    fun Main() {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
        ) {
            C0()
            C1()
            C2()
            C3()
        }
    }

    @Composable
    private fun C0() {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .height(128.dp)
                .padding(16.dp)
        ) {
            val imageRef = remember { createRef() }
            val textRef = remember { createRef() }
            Image(
                painter = painterResource(id = R.drawable.scenery),
                contentDescription = null,
                modifier = Modifier.constrainAs(imageRef) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    width = Dimension.ratio("16:9")
                },
                contentScale = ContentScale.Crop
            )
            Text(text = "ComposeComposeComposeComposeComposeComposeComposeComposeCompose",
                modifier = Modifier
                    .constrainAs(textRef) {
                        start.linkTo(imageRef.end, 16.dp)
                        top.linkTo(imageRef.top)
                        end.linkTo(parent.end)
                        width = Dimension.preferredWrapContent
                    }
            )
        }
    }

    @Composable
    fun C1() {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .height(128.dp)
                .padding(16.dp)
        ) {
            val (usernameTextRef, passwordTextRef, unInputRef, pwdInputRef, dividerRef) = remember {
                createRefs()
            }
            val unpwdBarrier = createEndBarrier(usernameTextRef, passwordTextRef)
            val gl = createGuidelineFromTop(0.5f)
            Text(text = "用户名", modifier = Modifier.constrainAs(usernameTextRef) {
                start.linkTo(parent.start)
                bottom.linkTo(gl, 16.dp)
            })
            Divider(
                thickness = 1.dp,
                color = Color.Gray,
                modifier = Modifier.constrainAs(dividerRef) {
                    top.linkTo(gl)
                    width = Dimension.matchParent
                })
            Text(text = "密码", modifier = Modifier.constrainAs(passwordTextRef) {
                start.linkTo(usernameTextRef.start)
                top.linkTo(gl, 16.dp)
            })
            OutlinedTextField(
                value = "",
                onValueChange = {},
                modifier = Modifier.constrainAs(unInputRef) {
                    start.linkTo(unpwdBarrier, 16.dp)
                    top.linkTo(usernameTextRef.top)
                    bottom.linkTo(usernameTextRef.bottom)
                    height = Dimension.fillToConstraints
                })
            OutlinedTextField(
                value = "",
                onValueChange = {},
                modifier = Modifier.constrainAs(pwdInputRef) {
                    start.linkTo(unInputRef.start)
                    top.linkTo(passwordTextRef.top)
                    bottom.linkTo(passwordTextRef.bottom)
                    height = Dimension.fillToConstraints
                })
        }
    }

    @Composable
    fun C2() {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .height(128.dp)
                .padding(16.dp)
        ) {
            val gl = createGuidelineFromTop(0.35f)
            val (topBg, bottomBg) = remember { createRefs() }
            Box(modifier = Modifier
                .constrainAs(topBg) {
                    top.linkTo(parent.top)
                    bottom.linkTo(gl)
                    width = Dimension.matchParent
                    height = Dimension.fillToConstraints
                }
                .background(Color.Red)
            )
            Box(modifier = Modifier
                .constrainAs(bottomBg) {
                    top.linkTo(gl)
                    bottom.linkTo(parent.bottom)
                    width = Dimension.matchParent
                    height = Dimension.fillToConstraints
                }
                .background(Color.Green)
            )
        }
    }

    @Composable
    fun C3() {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .height(1024.dp)
                .padding(16.dp)
                .background(Color.LightGray)
        ) {
            val (firstRef, secondRef, thirdRef, fifthRef) = remember { createRefs() }
            createVerticalChain(
                firstRef,
                secondRef,
                thirdRef,
                fifthRef,
                chainStyle = ChainStyle.Packed
            )
            Text(text = "故人西辞黄鹤楼，", modifier = Modifier.constrainAs(firstRef) {
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            })
            Text(text = "烟花三月下扬州。", modifier = Modifier.constrainAs(secondRef) {
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            })
            Text(text = "孤帆远影碧空尽，", modifier = Modifier.constrainAs(thirdRef) {
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            })
            Text(text = "唯见长江天际流。", modifier = Modifier.constrainAs(fifthRef) {
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            })
        }
    }

    @Preview(showSystemUi = true)
    @Composable
    fun PreviewMain() {
        Main()
    }

}