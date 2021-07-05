package me.zhang.laboratory.ui.view

import android.content.Context
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.res.ResourcesCompat
import me.zhang.laboratory.R

class ClearableEditText : AppCompatEditText {

    private var mClearButtonImage: Drawable? = null

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    private fun init() {
        setOpaqueClearButton()
        // If the clear (X) button is tapped, clear the text.
        setOnTouchListener { _, event ->
            if (compoundDrawablesRelative[2] != null) {
                val clearButtonStart: Int
                val clearButtonEnd: Int
                var isClearButtonClicked = false
                // Detect the touch in RTL or LTR layout direction.
                if (layoutDirection == View.LAYOUT_DIRECTION_RTL) {
                    // If RTL, get the end of the button on the left side.
                    clearButtonEnd = mClearButtonImage?.intrinsicWidth?.plus(paddingStart) ?: 0
                    // If the touch occurredd before the end of the button, set isClearButtonClicked to true
                    if (event.x < clearButtonEnd) {
                        isClearButtonClicked = true
                    }
                } else {
                    // Layout is LTR.
                    // Get the start of the button on the right side.
                    clearButtonStart =
                            width - (mClearButtonImage?.intrinsicWidth?.plus(paddingEnd) ?: 0)
                    // If the touch occurred after the start of the button, set isClearButtonClicked to true.
                    if (event.x > clearButtonStart) {
                        isClearButtonClicked = true
                    }
                }
                // Check for actions if the button is tapped.
                if (isClearButtonClicked) {
                    // Check for ACTION_DOWN (always occurs before ACTION_UP).
                    if (event.action == MotionEvent.ACTION_DOWN) {
                        // Switch to the black version of clear button.
                        showBlackClearButton()
                    }
                    // Check for ACTION_UP.
                    if (event.action == MotionEvent.ACTION_UP) {
                        // Switch to the opaque version of clear button.
                        showOpaqueClearButton()
                        // Clear the text and hide the clear button.
                        text?.clear()
                        return@setOnTouchListener true
                    }
                }
            }
            return@setOnTouchListener false
        }
        // If the text changes, show or hide the clear (X) button.
        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                if (TextUtils.isEmpty(s?.toString())) {
                    hideClearButton()
                } else {
                    showOpaqueClearButton()
                }
            }

        })
    }

    private fun showBlackClearButton() {
        setBlackClearButton()
        showClearButton()
    }

    private fun showOpaqueClearButton() {
        setOpaqueClearButton()
        showClearButton()
    }

    private fun setOpaqueClearButton() {
        mClearButtonImage = ResourcesCompat.getDrawable(resources, R.drawable.ic_clear_opaque_24dp, null)
    }

    private fun setBlackClearButton() {
        mClearButtonImage = ResourcesCompat.getDrawable(resources, R.drawable.ic_clear_black_24dp, null)
    }

    /**
     * Show the clear (X) button.
     */
    private fun showClearButton() {
        setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, mClearButtonImage, null)
    }

    /**
     * Hides the clear button.
     */
    private fun hideClearButton() {
        setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, null, null)
    }

}