package com.example.academy_weather.utils

import com.example.academy_weather.data.model.ApiServices
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {

    @Provides
    @Singleton
    fun provideBaseUrl() = BASE_URL

    @Provides
    @Singleton
    fun provideTimeOut() = TIME_OUT

    @Provides
    @Singleton
    fun provideGson(): Gson = GsonBuilder().setLenient().create()

    @Provides
    @Singleton
    @Named(HEADER_LOGGING_INTERCEPTOR)
    fun provideHeaderHttpLoggingInterceptor() = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.HEADERS
    }

    @Provides
    @Singleton
    @Named(BODY_LOGGING_INTERCEPTOR)
    fun provideBodyHttpLoggingInterceptor() = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    @Provides
    @Singleton
    fun provideClient(
        time_out: Long,
        @Named(HEADER_LOGGING_INTERCEPTOR) header: HttpLoggingInterceptor,
        @Named(BODY_LOGGING_INTERCEPTOR) body :HttpLoggingInterceptor
    ) = OkHttpClient.Builder()
        .connectTimeout(time_out, TimeUnit.SECONDS)
        .readTimeout(time_out, TimeUnit.SECONDS)
        .addInterceptor(header)
        .addInterceptor(body)
        .build()

    @Provides
    @Singleton
    fun provideService( base_url :String, client :OkHttpClient, gson: Gson ): ApiServices = Retrofit.Builder()
        .baseUrl(base_url)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .build()
        .create(ApiServices::class.java)
}