package com.c2.arenafinder.data.repository

import androidx.lifecycle.MutableLiveData
import com.c2.arenafinder.api.retrofit.RetrofitClient
import com.c2.arenafinder.data.response.AktivitasResponse

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Mengelola data-data yang didapatkan dari server ke halaman aktivitas
 *
 */
class AktivitasRepository {

    private val _aktivitasData = MutableLiveData<AktivitasResponse>()
    val aktivitasData get() = _aktivitasData

    suspend fun fetchAktivitasData() = withContext(Dispatchers.IO) {
        _aktivitasData.postValue(
            RetrofitClient.getInstance().aktivitasPage().execute().body()
        )
    }

}