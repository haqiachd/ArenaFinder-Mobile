package com.c2.arenafinder.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.c2.arenafinder.data.repository.UsersRepository
import com.c2.arenafinder.viewmodel.UsersViewModel

class UsersViewModelFactory(
    private val repository: UsersRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(UsersViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return UsersViewModel(repository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}