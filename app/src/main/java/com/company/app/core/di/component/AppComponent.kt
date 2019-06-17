package com.company.app.core.di.component

import android.app.Application
import com.company.app.core.application.AppController
import com.company.app.core.di.module.*
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Component(
        modules = [
            ViewModelModule::class,
            NetworkModule::class,
            SessionPrefsModule::class,
            FragmentModule::class,
            ActivityModule::class,
            AndroidSupportInjectionModule::class]
)
@Singleton
interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    fun inject(appController: AppController)
}