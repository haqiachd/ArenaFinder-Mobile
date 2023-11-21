package com.c2.arenafinder.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.c2.arenafinder.api.retrofit.RetrofitClient
import com.c2.arenafinder.data.response.UsersResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UsersRepository {

    private val _changePassLogin = MutableLiveData<UsersResponse>()
    val changePassLogin : LiveData<UsersResponse> get() = _changePassLogin

    private val _deletePhoto = MutableLiveData<UsersResponse>()
    val deletePhoto : LiveData<UsersResponse> get() = _deletePhoto

    private val _isVerified = MutableLiveData<UsersResponse>()
    val isVerified : LiveData<UsersResponse> get() = _isVerified

    suspend fun modifyPassLogin(email: String, pwNow : String, pwNew : String) = withContext(Dispatchers.IO){
        _changePassLogin.postValue(
            RetrofitClient.getInstance().updatePasswordLogin(email, pwNow, pwNew).execute().body()
        )
    }

    suspend fun removePhoto(email : String) = withContext(Dispatchers.IO){
        _deletePhoto.postValue(
            RetrofitClient.getInstance().deletePhoto(email).execute().body()
        )
    }

    suspend fun fetchVerified(email: String) = withContext(Dispatchers.IO){
        _isVerified.postValue(
            RetrofitClient.getInstance().isVerified(email).execute().body()
        )
    }


}