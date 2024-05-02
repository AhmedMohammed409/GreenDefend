package com.example.greendefend.domin.model.weather

import com.google.gson.annotations.SerializedName


data class Current (

    @SerializedName("temp_c"             ) var tempC            : Double?    = null,
    @SerializedName("temp_f"             ) var tempF            : Double?    = null,
    @SerializedName("condition"          ) var condition        : Condition? = Condition(),
)