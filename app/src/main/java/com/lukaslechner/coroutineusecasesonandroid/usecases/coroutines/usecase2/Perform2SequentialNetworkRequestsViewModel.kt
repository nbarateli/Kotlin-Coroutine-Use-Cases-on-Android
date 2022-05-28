package com.lukaslechner.coroutineusecasesonandroid.usecases.coroutines.usecase2

import androidx.lifecycle.viewModelScope
import com.lukaslechner.coroutineusecasesonandroid.base.BaseViewModel
import com.lukaslechner.coroutineusecasesonandroid.mock.MockApi
import kotlinx.coroutines.launch

class Perform2SequentialNetworkRequestsViewModel(
    private val mockApi: MockApi = mockApi()
) : BaseViewModel<UiState>() {

    fun perform2SequentialNetworkRequest() {
        uiState.postValue(UiState.Loading)
        viewModelScope.launch {
            try {
                uiState.postValue(
                    UiState.Success(
                        mockApi.getAndroidVersionFeatures(
                            mockApi.getRecentAndroidVersions().last().apiLevel
                        )
                    )
                )
            } catch (e: Exception) {
                uiState.postValue(UiState.Error(e.message ?: "Request failed!"))
            }
        }
    }

}