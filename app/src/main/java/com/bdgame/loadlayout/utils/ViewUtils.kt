package com.bdgame.loadlayout.utils

import android.content.Context
import android.text.InputFilter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.Space
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.bdgame.loadlayout.app.AppRuntime

/**
 * Author: lianxinglun
 * Date: 2021/11/1
 * Desc: TODO: 需求文档url（可选），简单说明
 * SinceVer: TODO: 哪个版本添加的
 */

fun <T : View> T.gone() {
    visibility = View.GONE
}

fun <T : View> T.visible() {
    visibility = View.VISIBLE
}

fun <T : View> T.isVisible(): Boolean {
    return visibility == View.VISIBLE
}

fun <T : View> T.isGone(): Boolean {
    return visibility == View.GONE
}

private inline val mApplication get() = AppRuntime.application
private inline val inputMethodManager
    get() = mApplication.getSystemService(
        Context.INPUT_METHOD_SERVICE
    ) as InputMethodManager

fun View.hideSoftKeyboard() {
    inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
}

fun View.showSoftKeyboard() {
    isFocusable = true
    requestFocus()
    inputMethodManager.showSoftInput(this, 2)
}

fun Fragment.hideKeyboard() {
    context?.let {
        ContextCompat.getSystemService(it, InputMethodManager::class.java)?.toggleSoftInput(
            InputMethodManager.HIDE_IMPLICIT_ONLY, 0
        )
    }
}

val displayMetrics by lazy { AppRuntime.application.resources.displayMetrics }
val Float.dp: Int get() = (this * displayMetrics.density).toInt()
val Int.dp: Int get() = (this * displayMetrics.density).toInt()
val Int.dpf: Float get() = (this * displayMetrics.density)

fun View.setWidthHeight(width: Int, height: Int) {
    layoutParams = ViewGroup.LayoutParams(width, height)
}

inline fun View.setFrameLayoutWidthHeight(
    width: Int, height: Int, crossinline block: FrameLayout.LayoutParams.() ->
    Unit
) {
    layoutParams = FrameLayout.LayoutParams(width, height).apply(block)
}

fun View.setLinearWidthHeight(width: Int, height: Int, weight: Float) {
    layoutParams = LinearLayout.LayoutParams(width, height, weight)
}

inline fun View.setConstraintLayoutParams(
    width: Int, height: Int, crossinline block: ConstraintLayout.LayoutParams.() -> Unit
) {
    layoutParams = ConstraintLayout.LayoutParams(width, height).apply(block)
}

fun View.setConstraintLayoutParamsMatchParent() {
    layoutParams = ConstraintLayout.LayoutParams(0, 0).apply {
        startToStart = ConstraintLayout.LayoutParams.PARENT_ID
        endToEnd = ConstraintLayout.LayoutParams.PARENT_ID
        topToTop = ConstraintLayout.LayoutParams.PARENT_ID
        bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID
    }
}

fun View.setLinearLayoutWidthHeight(width: Int, height: Int, weight: Float) {
    layoutParams = LinearLayout.LayoutParams(width, height, weight)
}

inline fun View.setLinearLayoutWidthHeight(
    width: Int,
    height: Int,
    crossinline block: LinearLayout.LayoutParams.() -> Unit
) {
    layoutParams = LinearLayout.LayoutParams(width, height).apply(block)
}

inline fun <T : View> T.addTo(viewGroup: ViewGroup, action: T.() -> Unit) =
    apply(action).also {
        viewGroup.addView(it)
    }

fun View.addTo(viewGroup: ViewGroup) = viewGroup.addView(this)
fun View.addTo(viewGroup: ViewGroup, width: Int, height: Int) = viewGroup.addView(this, width, height)

val isScreenPortrait get() = mApplication.resources.configuration.orientation == 1

fun ViewGroup.inflate(layoutResId: Int): View = LayoutInflater.from(context).inflate(layoutResId, this, false)

val View.locationOnScreenY: Int
    get() {
        val location = IntArray(2)
        getLocationOnScreen(location)
        return location[1]
    }

fun TextView.setTextColor(color: Long) = setTextColor(color.toInt())
fun TextView.setMaxLength(maxLength: Int) {
    filters += InputFilter.LengthFilter(maxLength)
}

/**
 * 这个量得比较准
 */
fun TextView.measureTextWidthRestrict(text: String): Int {
    if (text.isEmpty()) {
        return 0
    }
    val widths = FloatArray(text.length)
    paint.getTextWidths(text, widths)
    return widths.sumOf { kotlin.math.ceil(it.toDouble()) }.toInt()
}

@ComposableSimulateWithLayoutParams
fun ViewGroup.Space(width: Int, height: Int) = Space(context).addTo(this, width, height)

fun View.setBackgroundColor(color: Long) = setBackgroundColor(color.toInt())

fun ViewGroup.MarginLayoutParams.setMarginEx(left: Int = 0, top: Int = 0, right: Int = 0, bottom: Int = 0) {
    setMargins(left, top, right, bottom)
}

fun View.setPaddingEx(left: Int = 0, top: Int = 0, right: Int = 0, bottom: Int = 0) {
    setPadding(left, top, right, bottom)
}