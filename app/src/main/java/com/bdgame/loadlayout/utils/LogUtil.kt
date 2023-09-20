package com.bdgame.loadlayout.utils

import android.util.Log

object KLog {

    fun d(tag: String, message: String) {
        Log.d(tag, message)
    }

    fun i(tag: String, message: String) {
        Log.i(tag, message)
    }

    fun e(tag: String, message: String) {
        Log.e(tag, message)
    }
}