package com.c2.arenafinder.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.c2.arenafinder.api.retrofit.RetrofitClient
import com.c2.arenafinder.data.response.HomeResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class HomeRepository {

    private val _homeData = MutableLiveData<HomeResponse>()
    val homeData : LiveData<HomeResponse> get() = _homeData

    suspend fun fetchHomeData() = withContext(Dispatchers.IO){
        _homeData.postValue(
            RetrofitClient.getInstance().homePage().execute().body()
        )
    }

}