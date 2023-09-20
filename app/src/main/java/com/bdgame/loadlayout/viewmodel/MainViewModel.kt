package com.bdgame.loadlayout.viewmodel

import androidx.lifecycle.ViewModel
import com.bdgame.loadlayout.layout.LayoutManager

/**
 * Author: lidongjie01
 * Date: 2023/9/20
 * Desc: 主页viewmodel
 * SinceVer: 1.0.0
 */
class MainViewModel : ViewModel() {

    val layoutContent = LayoutManager.layoutContent
}