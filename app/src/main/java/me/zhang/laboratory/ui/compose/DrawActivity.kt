package me.zhang.laboratory.ui.compose

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.ImageShader
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.ShaderBrush
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.zhang.laboratory.R
import kotlin.math.min
import kotlin.math.sin

class DrawActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { Main() }
    }

    @Composable
    fun Main() {
        val imageBitmap = ImageBitmap.imageResource(R.drawable.scenery)
        val sweepAngle by rememberSaveable {
            mutableFloatStateOf(130f)
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(32.dp),
                contentAlignment = Alignment.Center
            ) {
                Canvas(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    drawCircle(
                        color = Color.Gray,
                        style = Stroke(width = 32.dp.toPx())
                    )
                    drawArc(
                        color = Color.DarkGray,
                        startAngle = 0f,
                        sweepAngle = 132f,
                        useCenter = false,
                        style = Stroke(width = 32.dp.toPx(), cap = StrokeCap.Round)
                    )
                }
                Text(text = "${"%.2f".format(sweepAngle / 360 * 100)}%", fontSize = 48.sp)
            }
            Canvas(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(32.dp)
            ) {
                drawImage(
                    imageBitmap,
                    colorFilter = run {
                        ColorFilter.colorMatrix(
                            ColorMatrix().apply { setToSaturation(0f) }
                        )
                    }
                )
                drawPath(
                    path = buildWavePath(
                        width = imageBitmap.width.toFloat() / 2f,
                        height = imageBitmap.height.toFloat(),
                        amplitude = imageBitmap.height / 8f,
                        progress = 0.5f
                    ),
                    brush = ShaderBrush(ImageShader(imageBitmap)),
                    alpha = 0.75f
                )
            }
        }
    }

    private fun buildWavePath(
        width: Float,
        height: Float,
        amplitude: Float,
        progress: Float,
    ): Path {
        val adjustHeight = min(height * 0f.coerceAtLeast(1 - progress), amplitude)
        val adjustWidth = 2 * width
        val dp = 2f
        return Path().apply {
            reset()
            moveTo(0f, height)
            lineTo(0f, height * (1 - progress))
            if (progress > 0f && progress < 1f) {
                if (adjustHeight > 0) {
                    var x = dp
                    while (x < adjustWidth) {
                        lineTo(
                            x,
                            height * (1 - progress) - adjustHeight / 2f * sin(4.0 * Math.PI * x / adjustWidth).toFloat()
                        )
                        x += dp
                    }
                }
            }
            lineTo(adjustWidth, height * (1 - progress))
            lineTo(adjustWidth, height)
            close()
        }
    }

    @Preview(showSystemUi = true)
    @Composable
    fun PreviewMain() {
        Main()
    }
}