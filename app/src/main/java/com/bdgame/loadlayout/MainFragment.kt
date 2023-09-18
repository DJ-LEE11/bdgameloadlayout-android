package com.bdgame.loadlayout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import com.bdgame.loadlayout.ui.CommonTitle

/**
 * Author: lidongjie01
 * Date: 2023/9/18
 * Desc: 主页Fragment
 * SinceVer: 1.0.0
 */
class MainFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return ConstraintLayout(inflater.context).apply {
            fitsSystemWindows = true
            setBackgroundColor(0xFFFFFFFF.toInt())
            val titleView = CommonTitle("load view")
        }
    }
}