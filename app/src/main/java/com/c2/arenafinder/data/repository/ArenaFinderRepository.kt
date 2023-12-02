package com.c2.arenafinder.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.c2.arenafinder.api.retrofit.RetrofitClient
import com.c2.arenafinder.data.response.ArenaFinderResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ArenaFinderRepository {

    private val _cekKoneksi = MutableLiveData<ArenaFinderResponse>()
    val cekKoneksi : LiveData<ArenaFinderResponse> get() = _cekKoneksi

    suspend fun cekKoneksi() = withContext(Dispatchers.IO){
        _cekKoneksi.postValue(
            RetrofitClient.getInstance().cekKoneksiV().execute().body()
        )
    }

}