package com.bdgame.loadlayout.app

import androidx.multidex.MultiDexApplication
import com.bdgame.loadlayout.layout.LayoutManager

/**
 * Author: lidongjie01
 * Date: 2023/9/18
 * Desc: app
 * SinceVer: 1.0.0
 */
class BDGApplication : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        AppRuntime.application = this
        LayoutManager
    }
}