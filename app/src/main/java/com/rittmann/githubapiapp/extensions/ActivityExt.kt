package com.rittmann.githubapiapp.extensions

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

inline fun <reified T : ViewModel> AppCompatActivity.viewModelsFactory(factory: ViewModelProvider.Factory? = null): ViewModel {
    return ViewModelProvider(this, factory
        ?: ViewModelProvider.NewInstanceFactory()).get(T::class.java)
}