package com.whatever.caramel.core.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.whatever.caramel.core.crashlytics.CaramelCrashlytics
import io.github.aakira.napier.Napier
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

abstract class BaseViewModel<S : UiState, SE : UiSideEffect, I : UiIntent>(
    val savedStateHandle: SavedStateHandle,
    protected val caramelCrashlytics: CaramelCrashlytics,
) : ViewModel() {
    private val initialState: S by lazy { createInitialState(savedStateHandle) }

    protected abstract fun createInitialState(savedStateHandle: SavedStateHandle): S

    protected abstract suspend fun handleIntent(intent: I)

    private val _state = MutableStateFlow(initialState)
    val state = _state.asStateFlow()

    private val _sideEffect: MutableSharedFlow<SE> = MutableSharedFlow()
    val sideEffect = _sideEffect.asSharedFlow()

    protected val currentState: S
        get() = _state.value

    protected val coroutineExceptionHandler =
        CoroutineExceptionHandler { _, throwable ->
            handleClientException(throwable)
        }

    fun intent(intent: I) {
        launch {
            caramelCrashlytics.log("${this@BaseViewModel::class.simpleName} > received intent : $intent")
            handleIntent(intent)
        }
    }

    protected fun reduce(reduce: S.() -> S) {
        val state = currentState.reduce()
        _state.value = state
    }

    protected fun postSideEffect(sideEffect: SE) {
        viewModelScope.launch { _sideEffect.emit(sideEffect) }
    }

    protected inline fun launch(
        context: CoroutineContext = EmptyCoroutineContext,
        start: CoroutineStart = CoroutineStart.DEFAULT,
        crossinline action: suspend CoroutineScope.() -> Unit,
    ): Job =
        viewModelScope.launch(context + coroutineExceptionHandler, start = start) {
            action()
        }

    open fun handleClientException(throwable: Throwable) {
        Napier.d { throwable.message.toString() }
    }
}
