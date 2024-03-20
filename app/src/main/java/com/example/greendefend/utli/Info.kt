package com.example.greendefend.utli

import java.text.SimpleDateFormat
import java.util.Locale

class Info {

    private val currentTime=System.currentTimeMillis()
    private val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
    private val date= formatter.format(currentTime)!!

        fun getDate(): String {
            return date
        }
    fun getLanguage():String{
       return Locale.getDefault().language
    }
}