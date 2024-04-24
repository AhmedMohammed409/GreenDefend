package com.example.greendefend.data.local

import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.greendefend.domin.model.Disease
import com.example.greendefend.domin.model.account.ErrorRegister
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

@TypeConverters
class Converters {


    @TypeConverter
    fun fromDiseaseJson(json: String): List<Disease> {
        return Gson().fromJson(
            json,
            object : TypeToken<ArrayList<Disease>>() {}.type
        )
    }

}