package com.c2.arenafinder.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.c2.arenafinder.data.repository.HomeRepository
import com.c2.arenafinder.viewmodel.HomeViewModel

class HomeViewModelFactory(
    private val repository: HomeRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return HomeViewModel(repository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}