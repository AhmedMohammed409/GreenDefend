package com.example.greendefend.data.remote

import com.example.greendefend.domin.model.weather.AllWeather
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiServiceWeather {

    @GET("data/2.5/forecast")
    suspend fun getWeather(
        @Query("lat")lat:Float,
        @Query("lon")lon:Float,
        @Query("appid")appid:String,
       @Query("lang") lang: String,
    ): Response<AllWeather>



}