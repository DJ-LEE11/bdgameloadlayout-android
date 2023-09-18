package com.bdgame.loadlayout.utils

import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope

/**
 * Author: lianxinglun
 * Date: 2022/1/4
 * Desc: Fragment相关工具方法
 * SinceVer: 1.9.0
 */


/**
 * Navigation在跳转其他页面再跳回来的时候，正常会重新创建view
 * 所以onViewCreated里面要是要绑定stateFlow的话，应该用这个viewLifecycleScope
 * 如果添加了额外逻辑，view是keep住的，就应该用Fragment本身的lifecycleScope
 */
val Fragment.viewLifecycleScope get() = viewLifecycleOwner.lifecycleScope