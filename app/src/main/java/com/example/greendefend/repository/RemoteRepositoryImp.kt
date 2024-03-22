package com.example.greendefend.repository

import com.example.greendefend.model.RegisterData
import com.example.greendefend.model.weather.CurrentWeather
import com.example.greendefend.remote.ApiServiceServer
import com.example.greendefend.remote.ApiServiceWeather
import retrofit2.Response
import javax.inject.Inject


class RemoteRepositoryImp @Inject constructor(
    private var apiServiceServer: ApiServiceServer,
    private var apiServiceWeather: ApiServiceWeather

) {
    fun signup(
        firstName: String,
        middleName: String,
        lastName: String,
        email: String,
        number: String,
        dateOfBirth: String,
        gender: String, password: String
    ): Response<RegisterData> {
        return apiServiceServer.signup(firstName, middleName, lastName, email, number, dateOfBirth, gender, password)
    }

//    fun login() {
//
//    }
//
//    fun getInfo() {
//
//    }

//    suspend fun getWeather(
//        key: String,
//        location: String,
//        days: Int,
//        date: String,
//        lang: String
//    ): Response<Weather> = apiServiceWeather.getWeather(key, location, days, date, lang)

    suspend fun getCurrentWeather(
        key: String,
        location: String,
        days: Int,
        date: String,
        lang: String
    ):Response<CurrentWeather> {
        return apiServiceWeather.getCurrentWeather(key, location, days, date, lang)}



}