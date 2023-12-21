package com.c2.arenafinder.api.retrofit

import androidx.annotation.StringRes

/**
 * Untuk menhandle state dari data retrofit, misalnya saat data sedang loading, saat data gagal didapatkan
 * atau saat data berhasil didapatkan
 *
 */
sealed class RetrofitState<out T> {
    data class Loading(val status: Boolean) : RetrofitState<Nothing>()
    data class Success<T>(val data: T) : RetrofitState<T>()
    data class Error(val message: String, @StringRes val messageInt: Int = 0) : RetrofitState<Nothing>()
}
