package com.c2.arenafinder.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.c2.arenafinder.data.repository.ReferensiRepository
import com.c2.arenafinder.viewmodel.ReferensiViewModel

/**
 * Digunakan untuk menghubungkan class ReferensiRepository dengan ReferensiViewMddel
 *
 */
class ReferensiViewModelFactory(
    private val repository: ReferensiRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(ReferensiViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return ReferensiViewModel(repository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel Class")
    }

}