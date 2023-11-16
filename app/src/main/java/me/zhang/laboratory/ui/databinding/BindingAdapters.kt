package me.zhang.laboratory.ui.databinding

import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.databinding.BindingConversion
import androidx.databinding.adapters.ListenerUtil
import com.bumptech.glide.Glide
import me.zhang.laboratory.R
import me.zhang.laboratory.utils.dp

@BindingAdapter("app:goneUnless")
fun goneUnless(view: View, visible: Boolean) {
    view.visibility = if (visible) View.VISIBLE else View.GONE
}

@BindingAdapter("app:paddingStart")
fun setPaddingLeft(view: View, oldPadding: Int, newPadding: Int) {
    if (oldPadding != newPadding) {
        view.setPadding(
            newPadding.dp().toInt(),
            view.paddingTop,
            view.paddingRight,
            view.paddingBottom
        )
    }
}

@BindingAdapter(value = ["app:imageUrl", "app:placeholder", "app:error"], requireAll = false)
fun loadImage(view: ImageView, imageUrl: String, placeholder: Drawable?, error: Drawable?) {
    Glide.with(view)
        .load(imageUrl)
        .placeholder(placeholder)
        .error(error)
        .into(view)
}

@BindingAdapter("app:onLayoutChange")
fun setOnLayoutChangeListener(
    view: View,
    oldValue: View.OnLayoutChangeListener?,
    newValue: View.OnLayoutChangeListener?
) {
    if (oldValue != null) {
        view.removeOnLayoutChangeListener(oldValue)
    }
    if (newValue != null) {
        view.addOnLayoutChangeListener(newValue)
    }
}

// Translation from provided interfaces in Java:
interface OnViewDetachedFromWindow {
    fun onViewDetachedFromWindow(v: View)
}

interface OnViewAttachedToWindow {
    fun onViewAttachedToWindow(v: View)
}

@BindingAdapter(
    "app:onViewDetachedFromWindow",
    "app:onViewAttachedToWindow",
    requireAll = false
)
fun setListener(view: View, detach: OnViewDetachedFromWindow?, attach: OnViewAttachedToWindow?) {
    val newListener: View.OnAttachStateChangeListener? = if (detach == null && attach == null) {
        null
    } else {
        object : View.OnAttachStateChangeListener {
            override fun onViewAttachedToWindow(v: View) {
                attach?.onViewAttachedToWindow(v)
            }

            override fun onViewDetachedFromWindow(v: View) {
                detach?.onViewDetachedFromWindow(v)
            }
        }
    }

    val oldListener: View.OnAttachStateChangeListener? =
        ListenerUtil.trackListener(view, newListener, R.id.onAttachStateChangeListener)
    if (oldListener != null) {
        view.removeOnAttachStateChangeListener(oldListener)
    }
    if (newListener != null) {
        view.addOnAttachStateChangeListener(newListener)
    }
}

@BindingConversion
fun convertColorToDrawable(color: Int) = ColorDrawable(color)