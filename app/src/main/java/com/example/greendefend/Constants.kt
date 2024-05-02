package com.example.greendefend

import android.content.Context
import android.graphics.Color
import android.net.Uri
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import javax.inject.Inject


object Constants{

        const val BASE_URL_WEATHER="http://api.weatherapi.com/v1/"
        const val BASE_URL_SERVICE="http://farisnho.runasp.net/api/"
        const val KEY="2b910f5a4be44d3c903212503241403"
       const val APP_DATA_STORE_NAME="GreenDefend"

     var  Token:String=""
     var Id:String=""
     var Email:String=""
     var Name:String=""
     var Country:String=""
     var Bio:String?=null
    var imageUrl:Uri?=null
    var fileName:String="be happy"




        fun provideProjectName(context: Context): SpannableString {
            val span = SpannableString(context.getString(R.string.green_defend))
            val fcBlack = ForegroundColorSpan(Color.BLACK)
            val fcGreen =
                ForegroundColorSpan(context.resources.getColor(R.color.green_name, context.theme))
            val size = context.getString(R.string.green_defend).length
            span.setSpan(fcBlack, 0, size / 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            span.setSpan(fcGreen, size / 2, size, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

            return span
    }
}
