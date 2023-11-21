package com.c2.arenafinder.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.c2.arenafinder.api.retrofit.RetrofitClient
import com.c2.arenafinder.api.retrofit.RetrofitState
import com.c2.arenafinder.data.repository.AktivitasRepository
import com.c2.arenafinder.data.response.AktivitasResponse
import kotlinx.coroutines.launch

class AktivitasViewModel(
    private val repository: AktivitasRepository
) : ViewModel() {

    private val _aktivitasData : MutableLiveData<RetrofitState<AktivitasResponse>> by lazy {
        MutableLiveData<RetrofitState<AktivitasResponse>>().also {
            fetchAktivitas()
        }
    }

    fun getAktivitasData() : LiveData<RetrofitState<AktivitasResponse>> = _aktivitasData

    fun fetchAktivitas(){
        viewModelScope.launch {
            try {
                // get data dari server
                _aktivitasData.value = RetrofitState.Loading(true)
                repository.fetchAktivitasData()
                val response = repository.aktivitasData
                // cek dan return state dari data
                if (response.value?.status?.lowercase() == RetrofitClient.SUCCESSFUL_RESPONSE){
                    _aktivitasData.value = RetrofitState.Success(response.value!!)
                }else{
                    _aktivitasData.value = RetrofitState.Error(response.value?.message.toString())
                }
            }catch (ex : Throwable){
                ex.printStackTrace()
                _aktivitasData.value = RetrofitState.Error(ex.message.toString())
            }
        }
    }
}