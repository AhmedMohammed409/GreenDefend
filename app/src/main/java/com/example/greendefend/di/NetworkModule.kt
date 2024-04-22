package com.example.greendefend.di

import com.example.greendefend.Constants
import com.example.greendefend.date.remote.ApiServiceServer
import com.example.greendefend.date.remote.ApiServiceWeather
import com.example.greendefend.date.remote.MyInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

        private var client= OkHttpClient.Builder().apply {
        addInterceptor(MyInterceptor())
    }.build()

//        .client(client)

    @Provides
    @Singleton
    fun provideWeatherApi(): ApiServiceWeather {
        return  Retrofit.Builder().baseUrl(Constants.BaseUrlWeather)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(ApiServiceWeather::class.java) }


    @Provides
    @Singleton
    fun provideAccountApi(): ApiServiceServer {
        return  Retrofit.Builder().baseUrl(Constants.BaseUrlService)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build().create(ApiServiceServer::class.java)
    }


}