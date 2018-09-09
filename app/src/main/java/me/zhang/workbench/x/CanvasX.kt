package me.zhang.workbench.x

import android.graphics.Canvas
import android.os.Build
import android.support.annotation.RequiresApi
import android.text.*
import androidx.graphics.withTranslation
import androidx.util.lruCache

/**
 * Created by Zhang on 2018/4/21 22:44.
 */
// https://medium.com/over-engineering/drawing-multiline-text-to-canvas-on-android-9b98f0bfa16a

@RequiresApi(Build.VERSION_CODES.O)
fun Canvas.drawMultilineText(
        text: CharSequence,
        textPaint: TextPaint,
        width: Int,
        x: Float,
        y: Float,
        start: Int = 0,
        end: Int = text.length,
        alignment: Layout.Alignment = Layout.Alignment.ALIGN_NORMAL,
        textDir: TextDirectionHeuristic = TextDirectionHeuristics.FIRSTSTRONG_LTR,
        spacingMult: Float = 1f,
        spacingAdd: Float = 0f,
        includePad: Boolean = true,
        ellipsizedWidth: Int = width,
        ellipsize: TextUtils.TruncateAt? = null,
        maxLines: Int = Int.MAX_VALUE,
        breakStrategy: Int = Layout.BREAK_STRATEGY_SIMPLE,
        hyphenationFrequency: Int = Layout.HYPHENATION_FREQUENCY_NONE,
        justificationMode: Int = Layout.JUSTIFICATION_MODE_NONE) {

    val cacheKey = "$text-$start-$end-$textPaint-$width-$alignment-$textDir-" +
            "$spacingMult-$spacingAdd-$includePad-$ellipsizedWidth-$ellipsize-" +
            "$maxLines-$breakStrategy-$hyphenationFrequency-$justificationMode"

    val staticLayout = StaticLayoutCache[cacheKey]
            ?: StaticLayout.Builder.obtain(text, start, end, textPaint, width)
                    .setAlignment(alignment)
                    .setTextDirection(textDir)
                    .setLineSpacing(spacingAdd, spacingMult)
                    .setIncludePad(includePad)
                    .setEllipsizedWidth(ellipsizedWidth)
                    .setEllipsize(ellipsize)
                    .setMaxLines(maxLines)
                    .setBreakStrategy(breakStrategy)
                    .setHyphenationFrequency(hyphenationFrequency)
                    .setJustificationMode(justificationMode)
                    .build().apply { StaticLayoutCache[cacheKey] = this }

    staticLayout.draw(this, x, y)
}

@RequiresApi(Build.VERSION_CODES.M)
fun Canvas.drawMultilineText(
        text: CharSequence,
        textPaint: TextPaint,
        width: Int,
        x: Float,
        y: Float,
        start: Int = 0,
        end: Int = text.length,
        alignment: Layout.Alignment = Layout.Alignment.ALIGN_NORMAL,
        textDir: TextDirectionHeuristic = TextDirectionHeuristics.FIRSTSTRONG_LTR,
        spacingMult: Float = 1f,
        spacingAdd: Float = 0f,
        includePad: Boolean = true,
        ellipsizedWidth: Int = width,
        ellipsize: TextUtils.TruncateAt? = null,
        maxLines: Int = Int.MAX_VALUE,
        breakStrategy: Int = Layout.BREAK_STRATEGY_SIMPLE,
        hyphenationFrequency: Int = Layout.HYPHENATION_FREQUENCY_NONE) {

    val cacheKey = "$text-$start-$end-$textPaint-$width-$alignment-$textDir-" +
            "$spacingMult-$spacingAdd-$includePad-$ellipsizedWidth-$ellipsize-" +
            "$maxLines-$breakStrategy-$hyphenationFrequency"

    val staticLayout = StaticLayoutCache[cacheKey]
            ?: StaticLayout.Builder.obtain(text, start, end, textPaint, width)
                    .setAlignment(alignment)
                    .setTextDirection(textDir)
                    .setLineSpacing(spacingAdd, spacingMult)
                    .setIncludePad(includePad)
                    .setEllipsizedWidth(ellipsizedWidth)
                    .setEllipsize(ellipsize)
                    .setMaxLines(maxLines)
                    .setBreakStrategy(breakStrategy)
                    .setHyphenationFrequency(hyphenationFrequency)
                    .build().apply { StaticLayoutCache[cacheKey] = this }

    staticLayout.draw(this, x, y)
}

fun Canvas.drawMultilineText(
        text: CharSequence,
        textPaint: TextPaint,
        width: Int,
        x: Float,
        y: Float,
        start: Int = 0,
        end: Int = text.length,
        alignment: Layout.Alignment = Layout.Alignment.ALIGN_NORMAL,
        spacingMult: Float = 1f,
        spacingAdd: Float = 0f,
        includePad: Boolean = true,
        ellipsizedWidth: Int = width,
        ellipsize: TextUtils.TruncateAt? = null) {

    val cacheKey = "$text-$start-$end-$textPaint-$width-$alignment-" +
            "$spacingMult-$spacingAdd-$includePad-$ellipsizedWidth-$ellipsize"

    // The public constructor was deprecated in API level 28,
    // but the builder is only available from API level 23 onwards
    val staticLayout = StaticLayoutCache[cacheKey]
            ?: if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                StaticLayout.Builder.obtain(text, start, end, textPaint, width)
                        .setAlignment(alignment)
                        .setLineSpacing(spacingAdd, spacingMult)
                        .setIncludePad(includePad)
                        .setEllipsizedWidth(ellipsizedWidth)
                        .setEllipsize(ellipsize)
                        .build()
            } else {
                StaticLayout(text, start, end, textPaint, width, alignment,
                        spacingMult, spacingAdd, includePad, ellipsize, ellipsizedWidth)
                        .apply { StaticLayoutCache[cacheKey] = this }
            }

    staticLayout.draw(this, x, y)
}

private fun StaticLayout.draw(canvas: Canvas, x: Float, y: Float) {
    canvas.withTranslation(x, y) {
        draw(this)
    }
}

private object StaticLayoutCache {

    private const val MAX_SIZE = 50 // Arbitrary max number of cached items
    private val cache = lruCache<String, StaticLayout>(MAX_SIZE)

    operator fun set(key: String, staticLayout: StaticLayout) {
        cache.put(key, staticLayout)
    }

    operator fun get(key: String): StaticLayout? {
        return cache[key]
    }
}