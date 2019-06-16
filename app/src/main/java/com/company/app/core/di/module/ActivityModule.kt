package com.company.app.core.di.module

import com.company.app.ui.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class ActivityModule {

    @ContributesAndroidInjector(modules = [FragmentModule::class])
    internal abstract fun contributeMainActivity(): MainActivity
}