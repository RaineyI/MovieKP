package com.raineyi.moviekp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.raineyi.moviekp.di.ApplicationScope
import javax.inject.Inject
import javax.inject.Provider

//@ApplicationScope
//с аннотацией не создаётся DaggerApplicationComponent в MovieApp
class ViewModelFactory @Inject constructor(
    private val viewModelProviders:
    @JvmSuppressWildcards Map<Class<out ViewModel>, Provider<ViewModel>>
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return viewModelProviders[modelClass]?.get() as T
    }
}