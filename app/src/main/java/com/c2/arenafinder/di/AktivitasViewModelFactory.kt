package com.c2.arenafinder.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.c2.arenafinder.data.repository.AktivitasRepository
import com.c2.arenafinder.viewmodel.AktivitasViewModel

/**
 * Digunakan untuk menghubungkan class AktivitasRepository dengan AktivitasViewModel
 *
 */
class AktivitasViewModelFactory(
    private val repository: AktivitasRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AktivitasViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return AktivitasViewModel(repository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}