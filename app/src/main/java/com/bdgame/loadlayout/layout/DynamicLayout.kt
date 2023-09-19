package com.bdgame.loadlayout.layout

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import com.bdgame.loadlayout.utils.addTo
import com.bdgame.loadlayout.utils.setWidthHeight

class DynamicLayout {
    fun rootView(context: Context): View = FrameLayout(context).apply {
        fitsSystemWindows = true
        setBackgroundColor(0xFFFFFFFF.toInt())
        setWidthHeight(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)

        TextView(context).addTo(this) {
            setWidthHeight(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
            textSize = 18f
            setTextColor(0xFF1F1F1F.toInt())
            text = "动态加载666666"
            setOnClickListener {
            }
        }
    }
}
