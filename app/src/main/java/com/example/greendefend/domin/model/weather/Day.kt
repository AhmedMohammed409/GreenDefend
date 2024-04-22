package com.example.greendefend.date.local.weather

import com.google.gson.annotations.SerializedName


data class Day (

    @SerializedName("maxtemp_c"            ) var maxtempC          : Double?    = null,
    @SerializedName("mintemp_c"            ) var mintempC          : Double?    = null,
    @SerializedName("avgtemp_c"            ) var avgtempC          : Double?    = null,
    @SerializedName("maxwind_mph"          ) var maxwindMph        : Int?       = null,
    @SerializedName("maxwind_kph"          ) var maxwindKph        : Double?    = null,
    @SerializedName("daily_will_it_rain"   ) var dailyWillItRain   : Int?       = null,
    @SerializedName("daily_chance_of_rain" ) var dailyChanceOfRain : Int?       = null,
    @SerializedName("daily_will_it_snow"   ) var dailyWillItSnow   : Int?       = null,
    @SerializedName("daily_chance_of_snow" ) var dailyChanceOfSnow : Int?       = null,
    @SerializedName("condition"            ) var condition         : Condition? = Condition(),

    )