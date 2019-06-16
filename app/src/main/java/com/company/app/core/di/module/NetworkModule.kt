package com.company.app.core.di.module

import android.app.Application
import android.content.pm.PackageManager
import android.os.Build
import com.company.app.BuildConfig
import com.company.app.commons.data.local.remote.ApiService
import com.company.app.core.constants.Constants
import com.company.app.core.constants.Times
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
class NetworkModule {

    @Provides
    @Singleton
    fun provideClient(application: Application, cache: Cache): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
            })
            .addInterceptor { chain ->
                var versionName = "Unknown"
                var versionCode = "Unknown"
                var packageName = "Unknown"
                try {
                    val packageInfo = application.packageManager.getPackageInfo(application.packageName, 0)
                    versionName = packageInfo.versionName
                    versionCode = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                        packageInfo.longVersionCode.toString()
                    } else {
                        packageInfo.versionCode.toString()
                    }
                    packageName = packageInfo.packageName
                } catch (e: PackageManager.NameNotFoundException) {
                    Timber.e(e)
                }

                val platform = Constants.PLATFORM
                val deviceVersion = Build.VERSION.RELEASE
                val deviceSdk = Build.VERSION.SDK_INT.toString()
                val deviceManufacture = Build.MANUFACTURER
                val deviceModel = Build.MODEL
                val deviceBrand = Build.BRAND

                val request = chain.request().newBuilder()
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Accept", "application/json")
                    .addHeader("Accept-Language", Locale.getDefault().language)
                    .addHeader("X-PLATFORM", platform)
                    .addHeader("X-LANG", Locale.getDefault().language)
                    .addHeader("X-TIME-ZONE", TimeZone.getDefault().id.toString())
                    .addHeader("X-APK-VERSION-NAME", versionName)
                    .addHeader("X-APK-VERSION-CODE", versionCode)
                    .addHeader("X-APK-PACKAGE-NAME", packageName)
                    .addHeader("X-DEVICE-VERSION", deviceVersion)
                    .addHeader("X-DEVICE-SDK", deviceSdk)
                    .addHeader("X-DEVICE-MANUFACTURE", deviceManufacture)
                    .addHeader("X-DEVICE-MODEL", deviceModel)
                    .addHeader("X-DEVICE-BRAND", deviceBrand)
                    .build()

                chain.proceed(request)
            }
            .connectTimeout(Times.TIMEOUT_NETWORK_CONNECT.toLong(), TimeUnit.SECONDS)
            .writeTimeout(Times.TIMEOUT_NETWORK_WRITE.toLong(), TimeUnit.SECONDS)
            .readTimeout(Times.TIMEOUT_NETWORK_READ.toLong(), TimeUnit.SECONDS)
            .cache(cache)
            .build()
    }

    @Provides
    @Singleton
    fun providesOkhttpCache(application: Application): Cache {
        val cacheSize = 10 * 1024 * 1024 // 10 MB
        return Cache(application.cacheDir, cacheSize.toLong())
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        client: OkHttpClient,
        rxJava2CallAdapterFactory: RxJava2CallAdapterFactory,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.API_URL)
            .client(client)
            .addConverterFactory(gsonConverterFactory)
            .addCallAdapterFactory(rxJava2CallAdapterFactory)
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit, apiServiceHolder: APIServiceHolder): ApiService {
        val apiService = retrofit.create(ApiService::class.java)
        apiServiceHolder.setAPIService(apiService)
        return apiService
    }

    @Provides
    @Singleton
    fun apiServiceHolder(): APIServiceHolder = APIServiceHolder()

    @Provides
    @Singleton
    fun providesGsonConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }

    @Provides
    @Singleton
    fun providesRxJavaCallAdapterFactory(): RxJava2CallAdapterFactory {
        return RxJava2CallAdapterFactory.create()
    }


    //REFRESH ID_USER
    class APIServiceHolder {

        private var apiService: ApiService? = null

        fun apiService(): ApiService? = apiService

        fun setAPIService(apiService: ApiService) {
            this.apiService = apiService
        }
    }
}
