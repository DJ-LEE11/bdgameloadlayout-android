package com.bdgame.loadlayout.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.bdgame.loadlayout.utils.KLog

/**
 * Author: lidongjie01
 * Date: 2023/9/20
 * Desc: adb命令监听
 * SinceVer: 1.0.0
 */
class AdbCommandReceiver : BroadcastReceiver() {

    companion object {
        private const val TAG = "AdbCommandReceiver"
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        intent?.action?.apply {
            if (this == "com.bdgame.loadlayout.UPATE_LAYOUT") {
                KLog.i(TAG, "receive adb intent:$intent")
            }
        }
    }
}