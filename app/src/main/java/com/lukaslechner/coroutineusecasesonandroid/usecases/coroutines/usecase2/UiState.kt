package com.lukaslechner.coroutineusecasesonandroid.usecases.coroutines.usecase2

import androidx.lifecycle.MutableLiveData
import com.lukaslechner.coroutineusecasesonandroid.mock.VersionFeatures

sealed class UiState {
    object Loading : UiState()
    data class Success(
        val versionFeatures: VersionFeatures
    ) : UiState()

    data class Error(val message: String) : UiState()
}

fun MutableLiveData<UiState>.postSuccess(data: VersionFeatures) {
    postValue(UiState.Success(data))
}

fun MutableLiveData<UiState>.postError(error: String) {
    postValue(UiState.Error(error))
}

fun MutableLiveData<UiState>.postError(error: Throwable) {
    postValue(UiState.Error(error.message ?: "Unknown Error"))
}

fun MutableLiveData<UiState>.postLoading() = postValue(UiState.Loading)