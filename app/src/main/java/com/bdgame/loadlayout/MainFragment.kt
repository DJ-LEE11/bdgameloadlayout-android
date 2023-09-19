package com.bdgame.loadlayout

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bdgame.loadlayout.ui.LoadDynamicLayoutFail

/**
 * Author: lidongjie01
 * Date: 2023/9/18
 * Desc: 主页Fragment
 * SinceVer: 1.0.0
 */
class MainFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return getRootView(inflater.context) ?: inflater.context.LoadDynamicLayoutFail()
    }
}

private fun getRootView(context: Context): View? {
    try {
        val loadLayoutClass = Class.forName("com.bdgame.loadlayout.layout.DynamicLayout")
        val loadLayoutInstance = loadLayoutClass.newInstance()
        val rootViewMethod = loadLayoutClass.getDeclaredMethod("rootView", Context::class.java)
        val view = rootViewMethod.invoke(loadLayoutInstance, context) as View
        return view
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return null
}
