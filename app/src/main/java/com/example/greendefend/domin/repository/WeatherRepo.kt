package com.example.greendefend.domin.repository

import com.example.greendefend.domin.model.weather.AllWeather
import com.example.greendefend.utli.NetworkResult
import retrofit2.Response

interface WeatherRepo {

  suspend fun  getWeather(
      lat:Float,
      lon:Float,
      key:String,
     lang: String,
    ): NetworkResult<Any>

//    suspend fun  getWeather(
//        lat:Float,
//        lon:Float,
//        key:String,
//        lang: String,
//    ): Response<AllWeather>


}