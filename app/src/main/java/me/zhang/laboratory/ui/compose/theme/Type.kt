package me.zhang.laboratory.ui.compose.theme

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import me.zhang.laboratory.R

val tiltNenoRegularFamily = FontFamily(Font(R.font.tilt_neon_regular, FontWeight.Normal))

val h1 = TextStyle(
    fontSize = 18.sp,
    fontFamily = tiltNenoRegularFamily,
    fontWeight = FontWeight.Bold
)

val h2 = TextStyle(
    fontSize = 14.sp,
    letterSpacing = 0.15.sp,
    fontFamily = tiltNenoRegularFamily,
    fontWeight = FontWeight.Bold
)

val caption = TextStyle(
    fontSize = 12.sp,
    fontFamily = tiltNenoRegularFamily,
    fontWeight = FontWeight.SemiBold
)