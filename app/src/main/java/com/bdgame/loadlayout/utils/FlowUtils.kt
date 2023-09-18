package com.bdgame.loadlayout.utils

import androidx.lifecycle.LifecycleCoroutineScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

/**
 * User: lxl
 * Date: 2021/10/12
 * Desc: 工具方法
 * SinceVer: 1.0.0
 */


val mStateInScope = CoroutineScope(Dispatchers.Unconfined + SupervisorJob())

/**
 * map没有StateFlow版本，搞一个
 */
inline fun <T, R> StateFlow<T>.mapToStateFlow(
    initValue: R, crossinline transform: suspend (value: T) -> R
): StateFlow<R> = map(transform).stateIn(mStateInScope, SharingStarted.Eagerly, initValue)

/**
 * 例如监听用户登录变化（userFlow)，而用户自身又有信息变化(infoFlow）[memberFlow]
 * 连接起来变成一个所有用户的信息变化flow
 *
 * 【注意】源flow的值为null的时候也会emit null（例如登出了userFlow值为null)
 */
inline fun <T, R> Flow<T>.memberFlowNullable(crossinline memberFlow: (T) -> Flow<R>?): Flow<R?> {
    val source = this
    return object : Flow<R?> {
        @InternalCoroutinesApi
        override suspend fun collect(collector: FlowCollector<R?>) {
            source.collect {
                memberFlow(it)?.collect { memberValue ->
                    collector.emit(memberValue)
                } ?: collector.emit(null)
            }
        }
    }
}

inline fun <T> Flow<T>.collectWhenStarted(
    lifecycleScope: LifecycleCoroutineScope,
    crossinline action: suspend (T) -> Unit
) {
    lifecycleScope.launchWhenStarted {
        collect { value ->
            action(value)
        }
    }
}

/**
 * fragment或者dialogFragment相关的应用resume后处理
 */
inline fun <T> Flow<T>.collectWhenResumed(
    lifecycleScope: LifecycleCoroutineScope,
    crossinline action: suspend (T) -> Unit
) = lifecycleScope.launchWhenResumed {
    collect { value ->
        action(value)
    }
}

/**
 * 例如监听用户登录变化（userFlow)，而用户自身又有信息变化(infoFlow）[memberFlow]
 * 连接起来变成一个所有用户的信息变化flow
 *
 * 【注意】源flow的值为null的时候也会emit null（例如登出了userFlow值为null)
 */
private inline fun <T, R> Flow<T?>.memberFlowNullableImpl(
    resultFlow: MutableSharedFlow<R?>,
    scope: CoroutineScope,
    crossinline memberFlow: T.() -> Flow<R>
): Flow<R?> {
    val source = this
    return resultFlow.apply {
        scope.launch(Dispatchers.Unconfined) {
            var collectMemberJob: Job? = null
            source.collect {
                collectMemberJob?.cancel()
                it?.memberFlow()?.let { flow ->
                    collectMemberJob = launch {
                        resultFlow.emitAll(flow)
                    }
                } ?: resultFlow.emit(null)
            }
        }
    }
}

/**
 * 这个不会保存状态，错过了就没有了，普通flow
 */
fun <T, R> Flow<T?>.memberFlow(scope: CoroutineScope, memberFlow: T.() -> Flow<R>): Flow<R?> =
    memberFlowNullableImpl(MutableSharedFlow(), scope, memberFlow)

/**
 * 这个会保存状态，StateFlow
 */
fun <T, R> Flow<T?>.memberFlowAsStateFlow(
    scope: CoroutineScope,
    memberFlow: T.() -> Flow<R>
): StateFlow<R?> {
    val stateFlow = MutableStateFlow<R?>(null)
    memberFlowNullableImpl(stateFlow, scope, memberFlow)
    return stateFlow
}

inline fun <T, R> Flow<T>.mapStateFlow(
    initValue: R, crossinline transform: suspend (value: T, R) -> R
): StateFlow<R> {
    val stateFlow = MutableStateFlow(initValue)
    mStateInScope.launch {
        collect {
            val result = transform(it, stateFlow.value)
            if (result != stateFlow.value) {
                stateFlow.tryEmit(result)
            }
        }
    }
    return stateFlow
}