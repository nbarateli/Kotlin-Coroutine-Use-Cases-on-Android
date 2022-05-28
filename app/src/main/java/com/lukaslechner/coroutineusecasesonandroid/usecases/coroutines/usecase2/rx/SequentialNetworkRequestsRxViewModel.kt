package com.lukaslechner.coroutineusecasesonandroid.usecases.coroutines.usecase2.rx

import com.lukaslechner.coroutineusecasesonandroid.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers

class SequentialNetworkRequestsRxViewModel(
    private val mockApi: RxMockApi = mockApi()
) : BaseViewModel<UiState>() {
    private val disposables = CompositeDisposable()

    fun perform2SequentialNetworkRequest() {
        mockApi.getRecentAndroidVersions()
            .doOnSubscribe { uiState.postValue(UiState.Loading) }
            .flatMap { mockApi.getAndroidVersionFeatures(it.last().apiLevel) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                uiState.postValue(UiState.Success(it))
            }, {
                uiState.postValue(UiState.Error(it.message ?: "Unknow error"))
            })
            .bindToLifeCycle()
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }

    private fun Disposable.bindToLifeCycle() = disposables.add(this)


}