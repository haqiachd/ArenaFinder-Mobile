package com.c2.arenafinder.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.c2.arenafinder.api.retrofit.RetrofitClient
import com.c2.arenafinder.api.retrofit.RetrofitState
import com.c2.arenafinder.data.repository.ReferensiRepository
import com.c2.arenafinder.data.response.ReferensiResponse
import kotlinx.coroutines.launch

class ReferensiViewModel(
    private val repository: ReferensiRepository
) : ViewModel() {

    private val _referensiData : MutableLiveData<RetrofitState<ReferensiResponse>> by lazy{
        MutableLiveData<RetrofitState<ReferensiResponse>>().also {
            fetchAktivitas()
        }
    }

    val referensiData : LiveData<RetrofitState<ReferensiResponse>> = _referensiData

    fun fetchAktivitas(){
        viewModelScope.launch {
            try {
                // get data server
                _referensiData.value = RetrofitState.Loading(true)
                repository.fetchReferensiData()
                val response = repository.referensiData
                // cek dan return state dari data
                if (response.value?.status?.lowercase() == RetrofitClient.SUCCESSFUL_RESPONSE){
                    _referensiData.value = RetrofitState.Success(response.value!!)
                }else{
                    _referensiData.value = RetrofitState.Error(response.value?.message.toString())
                }
            }catch (ex : Throwable){
                ex.printStackTrace()
                _referensiData.value = RetrofitState.Error(ex.message.toString())
            }
        }
    }
}