package com.c2.arenafinder.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.c2.arenafinder.api.retrofit.RetrofitClient
import com.c2.arenafinder.api.retrofit.RetrofitState
import com.c2.arenafinder.data.repository.HomeRepository
import com.c2.arenafinder.data.response.HomeResponse
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: HomeRepository
) : ViewModel() {

    private val _homeData : MutableLiveData<RetrofitState<HomeResponse>> by lazy {
        MutableLiveData<RetrofitState<HomeResponse>>().also {
            fetchHome()
        }
    }

    fun getHomeData() : LiveData<RetrofitState<HomeResponse>> = _homeData

    fun fetchHome(){

        viewModelScope.launch {
            try {
                // fetch data from server
                _homeData.value = RetrofitState.Loading(true)
                repository.fetchHomeData()
                val response = repository.homeData

                // cek and return state dari data
                if (response.value?.status?.lowercase() == RetrofitClient.SUCCESSFUL_RESPONSE){
                    _homeData.value = RetrofitState.Success(repository.homeData.value!!)
                }else{
                    _homeData.value = RetrofitState.Error(response.value?.message.toString())
                }
            }catch (ex : Throwable){
                _homeData.value = RetrofitState.Error(ex.message.toString())
            }
        }
    }

}