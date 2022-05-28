package com.lukaslechner.coroutineusecasesonandroid.usecases.coroutines.usecase2.callbacks

import com.lukaslechner.coroutineusecasesonandroid.base.BaseViewModel
import com.lukaslechner.coroutineusecasesonandroid.mock.AndroidVersion
import com.lukaslechner.coroutineusecasesonandroid.mock.VersionFeatures
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SequentialNetworkRequestsCallbacksViewModel(
    private val mockApi: CallbackMockApi = mockApi()
) : BaseViewModel<UiState>() {
    private var getAndroidFeaturesCall: Call<VersionFeatures>? = null
    private var androidVersions: Call<List<AndroidVersion>>? = null
    fun perform2SequentialNetworkRequest() {
        uiState.postValue(UiState.Loading)
        androidVersions = mockApi.getRecentAndroidVersions()
        androidVersions?.enqueue(object : Callback<List<AndroidVersion>> {
            override fun onResponse(
                call: Call<List<AndroidVersion>>,
                response: Response<List<AndroidVersion>>
            ) {
                if (response.isSuccessful) {
                    val versions = response.body() ?: emptyList()
                    val mostRecent = versions.last()

                    getAndroidFeaturesCall = mockApi.getAndroidVersionFeatures(mostRecent.apiLevel)
                    getAndroidFeaturesCall?.enqueue(getAndroidFeaturesCallRequest())
                } else {
                    uiState.postValue(UiState.Error("NETORK ERROR"))
                }
            }

            override fun onFailure(call: Call<List<AndroidVersion>>, t: Throwable) {
                uiState.postValue(UiState.Error(t.message.toString()))
            }

        })
    }

    private fun getAndroidFeaturesCallRequest() = object : Callback<VersionFeatures> {
        override fun onResponse(
            call: Call<VersionFeatures>,
            response: Response<VersionFeatures>
        ) {
            if (response.isSuccessful) {
                val features = response.body() ?: return
                uiState.postValue(UiState.Success(features))
            } else {
                uiState.postValue(UiState.Error("NETORK ERROR"))
            }
        }

        override fun onFailure(call: Call<VersionFeatures>, t: Throwable) {
            uiState.postValue(UiState.Error(t.message.toString()))
        }
    }

    override fun onCleared() {
        super.onCleared()
        androidVersions?.cancel()
        getAndroidFeaturesCall?.cancel()
    }
}