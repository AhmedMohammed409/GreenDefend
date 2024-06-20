package com.example.greendefend.domin.model.weather

import com.google.gson.annotations.SerializedName


data class Main (

  @SerializedName("temp"       ) var temp      : Float? = null,
  @SerializedName("feels_like" ) var feelsLike : Float? = null,
  @SerializedName("temp_min"   ) var tempMin   : Float? = null,
  @SerializedName("temp_max"   ) var tempMax   : Float? = null,
  @SerializedName("pressure"   ) var pressure  : Int?    = null,
  @SerializedName("sea_level"  ) var seaLevel  : Int?    = null,
  @SerializedName("grnd_level" ) var grndLevel : Int?    = null,
  @SerializedName("humidity"   ) var humidity  : Int?    = null,
  @SerializedName("temp_kf"    ) var tempKf    : Float?    = null

)