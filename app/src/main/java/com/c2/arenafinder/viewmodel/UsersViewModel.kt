package com.c2.arenafinder.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.c2.arenafinder.api.retrofit.RetrofitClient
import com.c2.arenafinder.api.retrofit.RetrofitState
import com.c2.arenafinder.data.model.UserModel
import com.c2.arenafinder.data.repository.UsersRepository
import com.c2.arenafinder.data.response.UsersResponse
import kotlinx.coroutines.launch

/**
 * Untuk menyediakan dan mengelola data user untuk tampilan fragment/activity di aplikasi.
 *
 */
class UsersViewModel(
    private val repository: UsersRepository
) : ViewModel() {

    private val _isLogin = MutableLiveData<RetrofitState<UsersResponse>>()
    fun isLogin() : LiveData<RetrofitState<UsersResponse>> = _isLogin

    private val _changePassLogin = MutableLiveData<RetrofitState<UsersResponse>>()
    fun changePassLogin() : LiveData<RetrofitState<UsersResponse>> = _changePassLogin

    private val _deletePhoto = MutableLiveData<RetrofitState<UsersResponse>>()
    fun deletePhoto() : LiveData<RetrofitState<UsersResponse>> = _deletePhoto

    private val _verifiedData = MutableLiveData<RetrofitState<UsersResponse>>()
    fun isVerified() : LiveData<RetrofitState<UsersResponse>> = _verifiedData

    private val _logoutData = MutableLiveData<RetrofitState<UsersResponse>>()
    fun logout() : LiveData<RetrofitState<UsersResponse>> = _logoutData

    fun checkIsLogin(email: String){
        viewModelScope.launch {
            try {
                // cek login
                _isLogin.value = RetrofitState.Loading(true)
                repository.checkIsLogin(email)
                val response = repository.isLogin
                // check and return state of data
                if (response.value?.status?.lowercase() == RetrofitClient.SUCCESSFUL_RESPONSE){
                    _isLogin.value = RetrofitState.Success(response.value!!)
                }else{
                    _isLogin.value = RetrofitState.Error(response.value?.message.toString())
                }
            }catch (ex : Throwable){
                ex.printStackTrace()
                _isLogin.value = RetrofitState.Error(ex.message.toString())
            }
        }
    }

    fun doChangePassLogin(email: String, pwNow : String, pwNew : String){
        viewModelScope.launch {
            try {
                // change password
                _changePassLogin.value = RetrofitState.Loading(true)
                repository.modifyPassLogin(email, pwNow, pwNew)
                val response = repository.changePassLogin
                // check and return state of data
                if (response.value?.status?.lowercase() == RetrofitClient.SUCCESSFUL_RESPONSE){
                    _changePassLogin.value = RetrofitState.Success(response.value!!)
                }else{
                    _changePassLogin.value = RetrofitState.Error(response.value?.message.toString())
                }
            }catch (ex : Throwable){
                ex.printStackTrace()
                _changePassLogin.value = RetrofitState.Error(ex.message.toString())
            }
        }
    }

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

    fun fetchVerified(email: String){
        viewModelScope.launch {
            try {
                // get verified
                _verifiedData.value = RetrofitState.Loading(true)
                repository.fetchVerified(email)
                val response = repository.isVerified
                // check and return state of data
                if (response.value?.status?.lowercase() == RetrofitClient.SUCCESSFUL_RESPONSE){
                    _verifiedData.value = RetrofitState.Success(response.value!!)
                }else{
                    _verifiedData.value = RetrofitState.Error(response.value?.message.toString())
                }
            }catch (ex : Throwable){
                ex.printStackTrace()
                _verifiedData.value = RetrofitState.Error(ex.message.toString())
            }
        }
    }

    fun doLogout(model : UserModel){
        viewModelScope.launch {
            try {
                // logout from server
                _logoutData.value = RetrofitState.Loading(true)
                repository.logout(model)
                val response = repository.logout
                // check and return state of data
                if (response.value?.status?.lowercase() == RetrofitClient.SUCCESSFUL_RESPONSE){
                    _logoutData.value = RetrofitState.Success(response.value!!)
                }else{
                    _logoutData.value = RetrofitState.Error(response.value?.message.toString())
                }
            }catch (ex : Throwable){
                ex.printStackTrace()
                _logoutData.value = RetrofitState.Error(ex.message.toString())
            }
        }
    }

}


/**
 *    private fun checkResponse(
_data : MutableLiveData<RetrofitState<UsersResponse>>,
response : LiveData<UsersResponse>
){
// check and return state of data
if (response.value?.status?.lowercase() == RetrofitClient.SUCCESSFUL_RESPONSE){
_data.value = RetrofitState.Success(response.value!!)
}else{
_data.value = RetrofitState.Error(response.value?.message.toString())
}
}
 */