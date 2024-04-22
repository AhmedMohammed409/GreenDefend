package com.example.greendefend.domin.model

import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.greendefend.domin.model.account.ErrorRegister
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

@TypeConverters
class Converters {

    @TypeConverter
    fun fromErrorLoginJson(json: String): ErrorRegister {
        return Gson().fromJson(
            json,
            object : TypeToken<ArrayList<ErrorRegister>>() {}.type
        )
    }
}