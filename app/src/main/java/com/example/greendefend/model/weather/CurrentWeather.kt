package com.example.greendefend.model.weather

import com.google.gson.annotations.SerializedName

data class CurrentWeather(
    @SerializedName("location" ) var location : Location? = Location(),
    @SerializedName("current"  ) var current  : Current?  = Current(), )
