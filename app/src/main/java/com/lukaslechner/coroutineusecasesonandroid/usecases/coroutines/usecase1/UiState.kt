package com.lukaslechner.coroutineusecasesonandroid.usecases.coroutines.usecase1

import androidx.lifecycle.MutableLiveData
import com.lukaslechner.coroutineusecasesonandroid.mock.AndroidVersion

sealed class UiState {
    object Loading : UiState()
    data class Success(val recentVersions: List<AndroidVersion>) : UiState()
    data class Error(val message: String) : UiState()
}

fun MutableLiveData<UiState>.postSuccess(data: List<AndroidVersion>) {
    postValue(UiState.Success(data))
}

fun MutableLiveData<UiState>.postError(error: String) {
    postValue(UiState.Error(error))
}

fun MutableLiveData<UiState>.postError(error: Throwable) {
    postValue(UiState.Error(error.message ?: "Unknown Error"))
}

fun MutableLiveData<UiState>.postLoading() = postValue(UiState.Loading)