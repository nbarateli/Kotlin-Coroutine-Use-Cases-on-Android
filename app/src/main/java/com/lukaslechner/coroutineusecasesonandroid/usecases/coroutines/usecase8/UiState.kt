package com.lukaslechner.coroutineusecasesonandroid.usecases.coroutines.usecase8

import androidx.lifecycle.MutableLiveData
import com.lukaslechner.coroutineusecasesonandroid.mock.AndroidVersion

sealed class UiState {
    sealed class Loading : UiState() {
        object LoadFromDb : Loading()
        object LoadFromNetwork : Loading()
    }

    data class Success(val dataSource: DataSource, val recentVersions: List<AndroidVersion>) :
        UiState()

    data class Error(val dataSource: DataSource, val message: String) : UiState()
}

fun MutableLiveData<UiState>.postSuccess(dataSource: DataSource, data: List<AndroidVersion>) {
    postValue(UiState.Success(dataSource, data))
}

fun MutableLiveData<UiState>.postError(dataSource: DataSource, error: String) {
    postValue(UiState.Error(dataSource, error))
}

fun MutableLiveData<UiState>.postError(dataSource: DataSource, error: Throwable) {
    postValue(UiState.Error(dataSource, error.message ?: "Unknown Error"))
}

fun MutableLiveData<UiState>.setError(dataSource: DataSource, error: String) {
    value = (UiState.Error(dataSource, error))
}

fun MutableLiveData<UiState>.setError(dataSource: DataSource, error: Throwable) {
    value = (UiState.Error(dataSource, error.message ?: "Unknown Error"))
}

fun MutableLiveData<UiState>.postLoading(dataSource: DataSource) = postValue(
    when (dataSource) {
        DataSource.DATABASE -> UiState.Loading.LoadFromDb
        else -> UiState.Loading.LoadFromNetwork
    }
)