package com.example.greendefend.domin.repository

import android.net.Uri
import com.example.greendefend.domin.model.forum.Comment
import com.example.greendefend.domin.model.forum.React
import com.example.greendefend.utli.NetworkResult
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response

interface RemoteRepository {

  suspend fun  getCurrentWeather(
    key: String,
    location: String,
    days: Int,
    date: String,
    lang: String
    ): NetworkResult<Any>
    suspend fun addImage(
        id: String,
        fileUri: Uri,
        fileRealPath: String
    ): Response<ResponseBody>
    fun getInfo()

}