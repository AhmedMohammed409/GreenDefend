package com.example.greendefend.domin.repository

import android.net.Uri
import com.example.greendefend.domin.model.account.Confirm
import com.example.greendefend.domin.model.account.Login
import com.example.greendefend.domin.model.account.User
import com.example.greendefend.domin.model.forum.Comment
import com.example.greendefend.domin.model.forum.React
import com.example.greendefend.domin.model.weather.CurrentWeather
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
    suspend fun register(user: User): NetworkResult<Any>
 
    suspend fun editProfile(
        body: RequestBody
    ): NetworkResult<Any>


    suspend fun addImage(
        id: String,
        fileUri: Uri,
        fileRealPath: String
    ): Response<ResponseBody>

    suspend fun login(login: Login): NetworkResult<Any>

    suspend fun confirmAccount(confirm: Confirm): NetworkResult<Any>
    suspend fun addPost(
        body:RequestBody): NetworkResult<Any>
    suspend fun getPosts():NetworkResult<Any>

    suspend fun addComment(
       comment: Comment
    ): NetworkResult<Any>

    suspend fun addReact(
        react: React
    ): NetworkResult<Any>
    fun getInfo()

}