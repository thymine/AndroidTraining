package me.zhang.laboratory.ui.compose

import android.content.res.Configuration
import androidx.compose.ui.tooling.preview.Preview

@Preview(
    showSystemUi = true,
    name = "Light Mode",
)
@Preview(
    showSystemUi = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    name = "Dark Mode",
)
annotation class PreviewDarkLight