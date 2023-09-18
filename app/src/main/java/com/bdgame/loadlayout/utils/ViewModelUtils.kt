package com.bdgame.loadlayout.utils

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/**
 * Author: lianxinglun
 * Date: 2021/11/4
 * Desc: ViewModel交互工具方法
 * SinceVer: 1.0.0
 */
val lifecycleRepositoryCollectScope by lazy { CoroutineScope(Dispatchers.Unconfined + SupervisorJob()) }

/**
 * 有生命周期的数据仓库
 */
interface ILifecycleAwareRepository {

    /**
     * 销毁释放资源
     */
    fun destroy()
}

/**
 * 跟随直播状态生成对应的直播生命周期关联的数据仓鼠
 * 即每隔直播间对应一份实例，开播就[createRepository]，关播则会destroy
 */
inline fun <T : ILifecycleAwareRepository, S> StateFlow<S?>.mapToLifecycleAwareRepository(
    crossinline createRepository: (S) -> T
): StateFlow<T?> {
    val stateFlow = MutableStateFlow(value?.let { createRepository(it) })
    lifecycleRepositoryCollectScope.launch {
        this@mapToLifecycleAwareRepository.collect {
            stateFlow.run {
                if (it == null) {
                    value?.destroy()
                    value = null
                } else {
                    if (value == null) {
                        value = createRepository(it)
                    }
                }
            }
        }
    }
    return stateFlow
}

inline fun <T : ILifecycleAwareRepository, S> mapFromLifecycleAwareRepository(
    source: StateFlow<S?>,
    crossinline createRepository: (S) -> T
) = source.mapToLifecycleAwareRepository(createRepository)