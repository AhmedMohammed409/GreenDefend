package com.example.greendefend.remote

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.Response

@Module
@InstallIn(SingletonComponent::class)
class MyInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader("content", "application/json")
            .build()
        return chain.proceed(request)
    }

}