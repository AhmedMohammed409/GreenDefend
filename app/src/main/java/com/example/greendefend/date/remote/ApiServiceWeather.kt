package com.example.greendefend.date.remote

import com.example.greendefend.date.local.weather.CurrentWeather
import com.example.greendefend.date.local.weather.Weather
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ApiServiceWeather {


    @GET("forecast.json")
    @Headers("content:application/json")
    suspend fun getWeather(
        @Query("key")key:String,
        @Query("q") q:String,
        @Query("days") days: Int,
        @Query("dt") dt: String,
        @Query("lang") lang: String,
    ): Response<Weather>

    @GET("current.json")
    @Headers("content:application/json")
    suspend fun getCurrentWeather(
        @Query("key")key:String,
        @Query("q") q:String,
        @Query("days") days: Int,
        @Query("dt") dt: String,
        @Query("lang") lang: String):Response<CurrentWeather>


}