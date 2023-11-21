package com.c2.arenafinder.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.c2.arenafinder.api.retrofit.RetrofitClient
import com.c2.arenafinder.api.retrofit.RetrofitState
import com.c2.arenafinder.data.repository.UsersRepository
import com.c2.arenafinder.data.response.UsersResponse
import kotlinx.coroutines.launch

class UsersViewModel(
    private val repository: UsersRepository
) : ViewModel() {

    private val _deletePhoto = MutableLiveData<RetrofitState<UsersResponse>>()
    fun deletePhoto() : LiveData<RetrofitState<UsersResponse>> = _deletePhoto

    fun doDeletePhoto(email: String){
        viewModelScope.launch {
            try {
                // remove foto
                _deletePhoto.value = RetrofitState.Loading(true)
                repository.removePhoto(email)
                val response = repository.deletePhoto
                // check and return state of data
                if (response.value?.status?.lowercase() == RetrofitClient.SUCCESSFUL_RESPONSE){
                    _deletePhoto.value = RetrofitState.Success(response.value!!)
                }else{
                    _deletePhoto.value = RetrofitState.Error(response.value?.message.toString())
                }
            }catch (ex : Throwable){
                ex.printStackTrace()
                _deletePhoto.value = RetrofitState.Error(ex.message.toString())
            }
        }
    }

}
