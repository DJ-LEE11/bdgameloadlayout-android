package com.bdgame.loadlayout.layout

import com.bdgame.loadlayout.app.AppRuntime
import com.bdgame.loadlayout.utils.KLog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
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

    private val mLayoutContent = MutableStateFlow<String?>(null)

    val layoutContent: StateFlow<String?> = mLayoutContent

    init {
        updateLayout()
    }

    fun updateLayout() {
        KLog.i(TAG, "updateLayout start")
        mScope.launch {
            val layoutFile = File("${AppRuntime.application.filesDir.path}/layout/layout.txt")
            if (layoutFile.exists()) {
                KLog.i(TAG, "updateLayout layoutFile exists")
                convertTextFileToString(layoutFile).apply {
                    KLog.i(TAG, "updateLayout content:$this")
                    mLayoutContent.tryEmit(this)
                }
            }
        }
    }

    private fun convertTextFileToString(file: File): String {
        val stringBuilder = StringBuilder()
        try {
            val bufferedReader = BufferedReader(FileReader(file))
            var line: String?
            while (bufferedReader.readLine().also { line = it } != null) {
                stringBuilder.append(line)
                stringBuilder.append("\n") // 如果需要保留换行符，可以注释掉这一行
            }
            bufferedReader.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return stringBuilder.toString()
    }
}