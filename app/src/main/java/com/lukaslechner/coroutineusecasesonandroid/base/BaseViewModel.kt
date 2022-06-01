package com.lukaslechner.coroutineusecasesonandroid.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

open class BaseViewModel<T> : ViewModel() {

    fun uiState(): LiveData<T> = uiState
    protected val uiState: MutableLiveData<T> = MutableLiveData()

    fun launchCo(
        context: CoroutineContext = EmptyCoroutineContext,
        start: CoroutineStart = CoroutineStart.DEFAULT, block: suspend CoroutineScope.() -> Unit
    ) = viewModelScope.launch(context, start, block)
}