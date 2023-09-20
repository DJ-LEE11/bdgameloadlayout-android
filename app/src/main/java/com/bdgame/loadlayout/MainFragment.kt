package com.bdgame.loadlayout

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bdgame.loadlayout.ui.LoadDynamicLayoutFail
import com.bdgame.loadlayout.utils.KLog
import com.bdgame.loadlayout.utils.collectWhenResumed
import com.bdgame.loadlayout.utils.viewLifecycleScope
import com.bdgame.loadlayout.viewmodel.MainViewModel

/**
 * Author: lidongjie01
 * Date: 2023/9/18
 * Desc: 主页Fragment
 * SinceVer: 1.0.0
 */
class MainFragment : Fragment() {

    companion object {
        private const val TAG = "MainFragment"
    }

    private val mViewModel by viewModels<MainViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return getRootView(inflater.context) ?: inflater.context.LoadDynamicLayoutFail()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mViewModel.layoutContent.collectWhenResumed(viewLifecycleScope) {
            KLog.i(TAG, "collect layoutContent:${it}")
        }
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
