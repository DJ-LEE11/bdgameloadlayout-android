package com.bdgame.loadlayout.ui

import android.view.Gravity
import android.view.View
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintLayout.LayoutParams.PARENT_ID
import com.bdgame.loadlayout.utils.ComposableSimulateWithLayoutParams
import com.bdgame.loadlayout.utils.addTo
import com.bdgame.loadlayout.utils.dp
import com.bdgame.loadlayout.utils.setConstraintLayoutParams

/**
 * Author: lidongjie01
 * Date: 2023/8/29
 * Desc: 通用ui
 * SinceVer: 2.54.0
 */

@ComposableSimulateWithLayoutParams
fun ConstraintLayout.CommonTitle(
    title: String,
) = ConstraintLayout(context).addTo(this) {
    id = View.generateViewId()
    setConstraintLayoutParams(MATCH_PARENT, 50.dp) {
        topToTop = PARENT_ID
    }
    TextView(context).addTo(this) {
        setConstraintLayoutParams(0.dp, WRAP_CONTENT) {
            startToStart = PARENT_ID
            topToTop = PARENT_ID
            bottomToBottom = PARENT_ID
            endToEnd = PARENT_ID
        }
        textSize = 18f
        gravity = Gravity.CENTER
        setTextColor(0xFF1F1F1F.toInt())
        text = title
    }
}