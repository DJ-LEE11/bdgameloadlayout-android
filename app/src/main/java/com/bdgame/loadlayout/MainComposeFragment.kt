package com.bdgame.loadlayout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import androidx.compose.ui.platform.ComposeView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintLayout.LayoutParams.PARENT_ID
import androidx.fragment.app.Fragment
import com.bdgame.loadlayout.ui.CommonTitle
import com.bdgame.loadlayout.ui.Main
import com.bdgame.loadlayout.utils.addTo
import com.bdgame.loadlayout.utils.dp
import com.bdgame.loadlayout.utils.setConstraintLayoutParams

/**
 * Author: lidongjie01
 * Date: 2023/9/18
 * Desc: 主页Fragment
 * SinceVer: 1.0.0
 */
class MainComposeFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return ConstraintLayout(inflater.context).apply {
            fitsSystemWindows = true
            setBackgroundColor(0xFFFFFFFF.toInt())
            val titleView = CommonTitle("compose view")
            ComposeView(context).addTo(this) {
                setConstraintLayoutParams(MATCH_PARENT, 0.dp) {
                    topToBottom = titleView.id
                    bottomToBottom = PARENT_ID
                    setContent { Main() }
                }
            }
        }
    }
}