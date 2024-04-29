package com.example.greendefend.di

import android.util.Log
import com.example.greendefend.Constants
import com.example.greendefend.data.remote.ApiServiceServer
import com.example.greendefend.data.remote.ApiServiceWeather
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    fun provideHttpClient():OkHttpClient{
        val httpLoggingInterceptor=HttpLoggingInterceptor()
        return  OkHttpClient().newBuilder().apply {
            addInterceptor{ chain ->
                val newRequest=chain.request().newBuilder()
                newRequest.addHeader("Accept","application/json")
                newRequest.addHeader("Authorization","Bearer ${Constants.Token}")
                newRequest.addHeader("Content-Type", "application/json")
                chain.proceed(newRequest.build())
            }
            addInterceptor(httpLoggingInterceptor)
            readTimeout(4,TimeUnit.MINUTES)
            writeTimeout(4,TimeUnit.MINUTES)
            callTimeout(4,TimeUnit.MINUTES)
        }.build()
    }


    @Provides
    @Singleton
    fun provideWeatherApi(): ApiServiceWeather {
        return  Retrofit.Builder().baseUrl(Constants.BASE_URL_WEATHER)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(ApiServiceWeather::class.java) }


    @Provides
    @Singleton
    fun provideAccountApi(okHttpClient: OkHttpClient): ApiServiceServer {
        return  Retrofit.Builder().baseUrl(Constants.BASE_URL_SERVICE)
            .addConverterFactory(GsonConverterFactory.create()).client(okHttpClient)
            .build().create(ApiServiceServer::class.java)
    }


}