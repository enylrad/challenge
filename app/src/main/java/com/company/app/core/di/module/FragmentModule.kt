package com.company.app.core.di.module

import com.company.app.ui.fragment.RequestCreateFragment
import com.company.app.ui.fragment.RequestListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentModule {
    /*
     * We define the name of the Fragment we are going
     * to inject the ViewModelFactory into. i.e. in our case
     * The name of the fragment: MovieListFragment
     */
    @ContributesAndroidInjector
    abstract fun contributeRequestListFragment(): RequestListFragment

    @ContributesAndroidInjector
    abstract fun contributeRequestCreateFragment(): RequestCreateFragment
}