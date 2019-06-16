package com.company.app.core.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.company.app.core.di.ViewModelKey
import com.company.app.core.factory.ViewModelFactory
import com.company.app.ui.viewmodel.CategoryViewModel
import com.company.app.ui.viewmodel.LocationViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
internal abstract class ViewModelModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(CategoryViewModel::class)
    protected abstract fun categoryViewModel(categoryViewModel: CategoryViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(LocationViewModel::class)
    protected abstract fun locationViewModel(locationViewModel: LocationViewModel): ViewModel
}