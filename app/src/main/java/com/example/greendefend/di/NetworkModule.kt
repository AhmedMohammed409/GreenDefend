package com.example.greendefend.di

import android.media.session.MediaSession.Token
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.greendefend.Constants
import com.example.greendefend.data.remote.ApiServiceServer
import com.example.greendefend.data.remote.ApiServiceWeather
import com.example.greendefend.data.repository.DataStorePrefrenceImpl
import com.example.greendefend.data.repository.DataStorePrefrenceImpl.Companion.Token_KEY
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Named
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
                newRequest.addHeader("Authorization", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJmYXJlcyIsImp0aSI6IjQ1YjM0MzE5LWY5MmQtNDU5ZC1iMzg4LWRhMDRhY2U5NGIxZSIsImVtYWlsIjoiZmFyc3J5ZjkxMkBnbWFpbC5jb20iLCJ1c2VySWQiOiIwYmQ2ZDYyMC05MTJlLTQxMGEtOTFkNi1kOGM5ZDQyNDI2NWMiLCJleHAiOjE3MTQ1OTI5MzcsImlzcyI6Imh0dHA6Ly9iZXR0ZXJjYWxsaG9tZWFwaWkuc29tZWUuY29tLyIsImF1ZCI6IioifQ.Jx7BLmJNwtZYg9KbbR01vkGmZN0MLdrsZifti9V_9ro")
                newRequest.addHeader("Content-Type","application/json")
                chain.proceed(newRequest.build())
            }
            addInterceptor(httpLoggingInterceptor)
            readTimeout(12,TimeUnit.MINUTES)
            writeTimeout(12,TimeUnit.MINUTES)
            callTimeout(12,TimeUnit.MINUTES)
        }.build()

    }

    @Provides
    @Singleton
    fun provideWeatherApi(): ApiServiceWeather {
        return  Retrofit.Builder().baseUrl(Constants.BaseUrlWeather)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(ApiServiceWeather::class.java) }


    @Provides
    @Singleton
    fun provideAccountApi(okHttpClient: OkHttpClient): ApiServiceServer {
        return  Retrofit.Builder().baseUrl(Constants.BaseUrlService)
            .addConverterFactory(GsonConverterFactory.create()).client(okHttpClient)
            .build().create(ApiServiceServer::class.java)
    }


}