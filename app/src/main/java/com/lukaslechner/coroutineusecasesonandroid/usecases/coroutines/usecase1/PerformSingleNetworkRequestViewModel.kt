package com.lukaslechner.coroutineusecasesonandroid.usecases.coroutines.usecase1

import com.lukaslechner.coroutineusecasesonandroid.base.BaseViewModel
import com.lukaslechner.coroutineusecasesonandroid.mock.AndroidVersion
import com.lukaslechner.coroutineusecasesonandroid.usecases.coroutines.usecase2.callbacks.CallbackMockApi
import com.lukaslechner.coroutineusecasesonandroid.usecases.coroutines.usecase2.callbacks.mockApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PerformSingleNetworkRequestViewModel(
    private val mockApi: CallbackMockApi = mockApi()
) : BaseViewModel<UiState>() {

    fun performSingleNetworkRequest() {
        mockApi.getRecentAndroidVersions().enqueue(
            object : Callback<List<AndroidVersion>> {
                override fun onResponse(
                    call: Call<List<AndroidVersion>>,
                    response: Response<List<AndroidVersion>>
                ) {
                    if (response.isSuccessful) {

                    }
                }

                override fun onFailure(call: Call<List<AndroidVersion>>, t: Throwable) {
                    uiState.postValue(UiState.Error("Unexpected"))
                }

            }
        )
    }
}