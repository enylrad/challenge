package com.company.app.core.di.module

import android.app.Application
import com.company.app.core.application.SessionPrefs
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class SessionPrefsModule {

    @Provides
    @Singleton
    fun providesSharedPreference(application: Application): SessionPrefs {
        return SessionPrefs.getInstance(application)
    }
}
