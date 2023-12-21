package com.c2.arenafinder.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.c2.arenafinder.api.retrofit.RetrofitClient
import com.c2.arenafinder.data.response.ReferensiResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Mengelola data-data yang didapatkan dari server ke halaman referensi
 *
 */
class ReferensiRepository {

    private val _referensiData = MutableLiveData<ReferensiResponse>()
    val referensiData : LiveData<ReferensiResponse> get() = _referensiData

    suspend fun fetchReferensiData() = withContext(Dispatchers.IO){
        _referensiData.postValue(
            RetrofitClient.getInstance().referensiPage().execute().body()
        )
    }

}