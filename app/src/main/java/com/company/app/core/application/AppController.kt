package com.company.app.core.application

import android.app.Activity
import android.app.Application
import com.company.app.BuildConfig
import com.company.app.core.di.component.DaggerAppComponent
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import timber.log.Timber
import javax.inject.Inject

/*
 * we use our AppComponent (now prefixed with Dagger)
 * to inject our Application class.
 * This way a DispatchingAndroidInjector is injected which is
 * then returned when an injector for an activity is requested.
 * */
class AppController : Application(), HasActivityInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    override fun activityInjector(): DispatchingAndroidInjector<Activity>? {
        return dispatchingAndroidInjector
    }

    override fun onCreate() {
        super.onCreate()
        initTimber()
        initDagger()

    }

    private fun initDagger() {
        DaggerAppComponent.builder()
                .application(this)
                .build()
                .inject(this)
    }

    private fun initTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }


}