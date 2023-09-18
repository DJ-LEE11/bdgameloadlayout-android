package com.bdgame.loadlayout.utils

import android.graphics.drawable.Drawable

import android.graphics.drawable.GradientDrawable

/**
 * Created by LiuBin.
 * shape制作工具类
 */

/**
 * 主体渐变红色
 * */
val BODY_RED_COLOR by lazy {
    intArrayOf(
        0xFFFF1F66.toInt(),
        0xFFFF4D4D.toInt()
    )
}

/**
 * 主题渐变红色的drawable
 * @param radius 圆角
 * 渐变红色 - 主体渐变红色
 * */
fun buildBodyRedShape(radius: Float): Drawable {
    return buildCustomShape(
        radius = radius, gradient = Gradient(
            gradientColors = BODY_RED_COLOR
        )
    )
}

/**
 *  构建自定义shape
 * */
fun buildCustomShape(
    radius: Float = 0f,
    gradient: Gradient? = null,
    stroke: Stroke? = null,
    fillColor: Int? = null,
    shape: Int = GradientDrawable.RECTANGLE
): Drawable {
    return buildCustomShape(
        cornetRadius = CornetRadius(
            radius, radius, radius, radius
        ),
        gradient = gradient,
        stroke = stroke,
        fillColor = fillColor,
        shape = shape
    )
}

/**
 *  构建自定义shape
 * */
fun buildCustomShape(
    cornetRadius: CornetRadius? = null,
    gradient: Gradient? = null,
    stroke: Stroke? = null,
    fillColor: Int? = null,
    shape: Int = GradientDrawable.RECTANGLE
): Drawable {
    val gradientDrawable = GradientDrawable()
    gradientDrawable.shape = shape
    cornetRadius?.let {
        gradientDrawable.cornerRadii = floatArrayOf(
            it.topLeftRadius,
            it.topLeftRadius,
            it.topRightRadius,
            it.topRightRadius,
            it.bottomRightRadius,
            it.bottomRightRadius,
            it.bottomLeftRadius,
            it.bottomLeftRadius
        )
    }
    gradient?.let {
        gradientDrawable.gradientType = it.gradientType
        gradientDrawable.colors = it.gradientColors
    }
    stroke?.let {
        gradientDrawable.setStroke(it.strokeWidth, it.strokeColor)
    }
    fillColor?.let {
        gradientDrawable.setColor(it)
    }
    return gradientDrawable
}

/**
 * 圆角
 * */
data class CornetRadius(
    val topLeftRadius: Float = 0f,
    val topRightRadius: Float = 0f,
    val bottomLeftRadius: Float = 0f,
    val bottomRightRadius: Float = 0f
)

/**
 * 渐变
 * */
data class Gradient(
    val gradientType: Int = GradientDrawable.LINEAR_GRADIENT,
    val gradientColors: IntArray,
    val angle: Int? = null
)
/**
 * 描边
 * */
data class Stroke(
    val strokeWidth: Int = 10,
    val strokeColor: Int = 0
)
