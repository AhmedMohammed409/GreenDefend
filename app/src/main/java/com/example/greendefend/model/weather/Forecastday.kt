package com.example.greendefend.model.weather

import com.google.gson.annotations.SerializedName


data class Forecastday (

    @SerializedName("date"       ) var date      : String?         = null,
    @SerializedName("day"        ) var day       : Day?            = Day(),
    @SerializedName("astro"      ) var astro     : Astro?          = Astro(),

)