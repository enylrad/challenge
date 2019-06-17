package com.company.app.core.di.module

import com.company.app.ui.fragment.RequestCreateFragment
import com.company.app.ui.fragment.RequestListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentModule {
    @ContributesAndroidInjector
    abstract fun contributeRequestListFragment(): RequestListFragment

    @ContributesAndroidInjector
    abstract fun contributeRequestCreateFragment(): RequestCreateFragment
}