package com.c2.arenafinder.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.c2.arenafinder.api.retrofit.RetrofitClient
import com.c2.arenafinder.api.retrofit.RetrofitState
import com.c2.arenafinder.data.repository.ArenaFinderRepository
import com.c2.arenafinder.data.response.ArenaFinderResponse
import kotlinx.coroutines.launch

class ArenaFinderViewModel(
    private val repository: ArenaFinderRepository
) : ViewModel() {

    private val _cekKoneksi = MutableLiveData<RetrofitState<ArenaFinderResponse>>()

    fun cekKoneksi() : LiveData<RetrofitState<ArenaFinderResponse>> = _cekKoneksi

    fun doCheckKoneksi(){
        viewModelScope.launch {
            try {
                // cek koneksi
                _cekKoneksi.value = RetrofitState.Loading(true)
                repository.cekKoneksi()
                val response = repository.cekKoneksi
                // check and return state of data
                if (response.value?.status?.lowercase() == RetrofitClient.SUCCESSFUL_RESPONSE){
                    _cekKoneksi.value = RetrofitState.Success(response.value!!)
                }else{
                    _cekKoneksi.value = RetrofitState.Error(response.value?.message.toString())
                }
            }catch (ex : Throwable){
                ex.printStackTrace()
                _cekKoneksi.value = RetrofitState.Error(ex.message.toString())
            }
        }
    }

}