package com.c2.arenafinder.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.c2.arenafinder.api.retrofit.RetrofitClient
import com.c2.arenafinder.data.response.UsersResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UsersRepository {

    private val _deletePhoto = MutableLiveData<UsersResponse>()
    val deletePhoto : LiveData<UsersResponse> get() = _deletePhoto

    suspend fun removePhoto(email : String) = withContext(Dispatchers.IO){
        _deletePhoto.postValue(
            RetrofitClient.getInstance().deletePhoto(email).execute().body()
        )
    }


}