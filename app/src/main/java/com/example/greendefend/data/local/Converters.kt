package com.example.greendefend.data.local

import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.greendefend.domin.model.Disease
import com.example.greendefend.domin.model.account.ErrorRegister
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

//@TypeConverters
class Converters {


//    @TypeConverter
//    fun fromDiseaseJson(json: String): Any {
//        return Gson().fromJson(
//            json,
//            object : TypeToken<Any>() {}.type
//        )
//    }

    fun convertDateTimeToDayName(dateTime: String): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val date = sdf.parse(dateTime)
        val calendar = Calendar.getInstance()
        date?.let {
            calendar.time = date
            calendar.get(Calendar.DAY_OF_WEEK)
            return SimpleDateFormat(
                "EEEE",
                Locale.getDefault()
            ).format(calendar.time) +"\n  "+ calendar.get(Calendar.HOUR_OF_DAY) +":"+ calendar.get(Calendar.MINUTE)
        }
        return ""
    }


}