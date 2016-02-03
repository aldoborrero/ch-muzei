package com.aldoborrero.muzei.ch.di.module

import android.content.Context
import com.aldoborrero.muzei.ch.BuildConfig
import com.aldoborrero.muzei.ch.api.CalvinHobbesApiService
import com.aldoborrero.muzei.ch.helper.Constants
import com.crashlytics.android.Crashlytics
import com.squareup.okhttp.OkHttpClient
import dagger.Module
import dagger.Provides
import io.fabric.sdk.android.Fabric
import retrofit.GsonConverterFactory
import retrofit.Retrofit
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

@Module class CalvinHobbesModule(private val context: Context) {

    @Provides public fun provideOkHttpClient(): OkHttpClient {
        val okHttpClient = OkHttpClient()
        okHttpClient.setConnectTimeout(Constants.OKHTTP_CONNECT_TIMEOUT, TimeUnit.MILLISECONDS)
        okHttpClient.setReadTimeout(Constants.OKHTTP_READ_TIMEOUT, TimeUnit.MILLISECONDS)
        okHttpClient.setWriteTimeout(Constants.OKHTTP_WRITE_TIMEOUT, TimeUnit.MILLISECONDS)
        return okHttpClient
    }

    @Provides public fun provideCallbackExecutor(): Executor {
        return Executors.newSingleThreadExecutor()
    }

    @Provides public fun provideGsonConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }

    @Provides public fun provideRetrofit(okHttpClient: OkHttpClient, executor: Executor,
                                         gsonConverterFactory: GsonConverterFactory): Retrofit {
        return Retrofit.Builder().baseUrl(BuildConfig.API_URL).callbackExecutor(executor).client(okHttpClient).addConverterFactory(gsonConverterFactory).build()
    }

    @Provides public fun provideCalvinHobbesApiService(retrofit: Retrofit): CalvinHobbesApiService {
        return retrofit.create(CalvinHobbesApiService::class.java)
    }

    @Provides public fun provideFabric(): Fabric {
        return Fabric.with(context, Crashlytics())
    }
}
