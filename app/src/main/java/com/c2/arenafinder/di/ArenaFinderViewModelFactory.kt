package com.c2.arenafinder.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.c2.arenafinder.data.model.ArenaFinderModel
import com.c2.arenafinder.data.repository.ArenaFinderRepository
import com.c2.arenafinder.viewmodel.ArenaFinderViewModel

class ArenaFinderViewModelFactory(
    val repository: ArenaFinderRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ArenaFinderViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return ArenaFinderViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }

}