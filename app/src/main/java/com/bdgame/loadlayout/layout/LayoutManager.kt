package com.bdgame.loadlayout.layout

import com.bdgame.loadlayout.app.AppRuntime
import com.bdgame.loadlayout.utils.KLog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.io.IOException

/**
 * Author: lidongjie01
 * Date: 2023/9/20
 * Desc: layout管理
 * SinceVer: 1.0.0
 */
object LayoutManager {

    private const val TAG = "LayoutManager"

    private val mScope = CoroutineScope(Dispatchers.Default + SupervisorJob())

    init {
        updateLayout()
    }

    fun updateLayout() {
        KLog.i(TAG, "updateLayout start")
        mScope.launch {
            val layoutFile = File("${AppRuntime.application.filesDir.path}/layout/layout.json")
            if (layoutFile.exists()) {
                KLog.i(TAG, "updateLayout layoutFile exists")
                convertJsonFileToString(layoutFile).apply {
                    KLog.i(TAG, "updateLayout json content:$this")
                }
            }
        }
    }

    private fun convertJsonFileToString(file: File): String {
        val stringBuilder = StringBuilder()

        try {
            val bufferedReader = BufferedReader(FileReader(file))
            var line: String?
            while (bufferedReader.readLine().also { line = it } != null) {
                stringBuilder.append(line)
            }
            bufferedReader.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return stringBuilder.toString()
    }
}