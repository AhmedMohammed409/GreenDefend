package com.example.greendefend.domin.model.weather

import com.google.gson.annotations.SerializedName


data class Weather (

    @SerializedName("location" ) var location : Location? = Location(),
    @SerializedName("current"  ) var current  : Current?  = Current(),
    @SerializedName("forecast" ) var forecast : Forecast? = Forecast()

)