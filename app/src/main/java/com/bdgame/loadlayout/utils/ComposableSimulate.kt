package com.bdgame.loadlayout.utils

/**
 * Author: lianxinglun
 * Date: 2022/6/23
 * SinceVer: 2.6.0
 * Desc: 假装自己是Compose
 */
@DslMarker
internal annotation class ComposableSimulate

/**
 * 假装自己是Compose，并且自带LayoutParams
 */
@DslMarker
internal annotation class ComposableSimulateWithLayoutParams