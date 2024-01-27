package com.example.greendefend.di

import android.content.Context
import androidx.window.layout.WindowMetricsCalculator
import com.example.greendefend.Constants.Companion.BASE_URL
import com.example.greendefend.data.remote.ApiService
import com.example.greendefend.data.remote.MyInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun getHight( @ApplicationContext context: Context):Float{
        val metrics = WindowMetricsCalculator.getOrCreate().computeCurrentWindowMetrics(context)
        val height = metrics.bounds.height()
        return height.toFloat()
    }
    @Provides
    @Singleton
    fun getWeight( @ApplicationContext context: Context):Float{
        val metrics = WindowMetricsCalculator.getOrCreate().computeCurrentWindowMetrics(context)
        val weight = metrics.bounds.width()
        return weight.toFloat()
    }

    private var client= OkHttpClient.Builder().apply {
        addInterceptor(MyInterceptor())
    }.build()

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return  Retrofit.Builder().baseUrl(BASE_URL).client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit):ApiService{
        return  retrofit.create(ApiService::class.java)
    }

}