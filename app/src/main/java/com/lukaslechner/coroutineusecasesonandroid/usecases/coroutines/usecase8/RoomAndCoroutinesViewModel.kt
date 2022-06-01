package com.lukaslechner.coroutineusecasesonandroid.usecases.coroutines.usecase8

import androidx.lifecycle.viewModelScope
import com.lukaslechner.coroutineusecasesonandroid.base.BaseViewModel
import com.lukaslechner.coroutineusecasesonandroid.mock.AndroidVersion
import com.lukaslechner.coroutineusecasesonandroid.mock.MockApi
import kotlinx.coroutines.async
import timber.log.Timber

class RoomAndCoroutinesViewModel(
    private val api: MockApi,
    private val database: AndroidVersionDao
) : BaseViewModel<UiState>() {

    fun loadData() {
        var currentSource = DataSource.DATABASE
        uiState.postLoading(currentSource)
        launchCo {
            try {
                if (loadFromDatabase()) {
                    return@launchCo
                }
                currentSource = DataSource.NETWORK
                loadFromNetwork()
            } catch (e: Exception) {
                Timber.e(e)
                uiState.postError(currentSource, e)
            }
        }
    }

    private suspend fun loadFromNetwork() {
        uiState.postLoading(DataSource.NETWORK)
        val dbVersions = api.getRecentAndroidVersions()
        dbVersions.map(AndroidVersion::mapToEntity).forEach { database.insert(it) }
        uiState.postSuccess(DataSource.NETWORK, dbVersions)
    }

    private suspend fun loadFromDatabase(): Boolean {
        return viewModelScope.async {
            val dbVersions =
                database.getAndroidVersions().map { AndroidVersion(it.apiLevel, it.name) }
            if (dbVersions.isNotEmpty()) {
                uiState.postSuccess(DataSource.DATABASE, dbVersions)
                return@async true
            } else {
                uiState.setError(DataSource.DATABASE, "Database is empty")
                return@async false
            }
        }.await()
    }

    fun clearDatabase() {
        launchCo { database.clear() }
    }
}


enum class DataSource(val dataSourceName: String) {
    DATABASE("Database"),
    NETWORK("Network")
}