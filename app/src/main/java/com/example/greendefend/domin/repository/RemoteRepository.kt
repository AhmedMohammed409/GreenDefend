package com.example.greendefend.domin.repository

import android.net.Uri
import com.example.greendefend.domin.model.account.Confirm
import com.example.greendefend.domin.model.account.Login
import com.example.greendefend.domin.model.account.ResponseLogin
import com.example.greendefend.domin.model.account.User
import com.example.greendefend.domin.model.forum.Comment
import com.example.greendefend.domin.model.forum.Post
import com.example.greendefend.domin.model.forum.React
import com.example.greendefend.domin.model.weather.CurrentWeather
import com.example.greendefend.utli.NetworkResult
import okhttp3.MultipartBody
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
    ):Response<CurrentWeather>
    suspend fun register(user: User):Response<String>
 
    suspend fun editProfile(
        body: RequestBody
    ): Response<ResponseBody?>


    suspend fun addImage(
        id: String,
        fileUri: Uri,
        fileRealPath: String
    ): Response<ResponseBody>

    suspend fun login(login: Login): Response<ResponseLogin>

    suspend fun confirmAccount(confirm: Confirm):Response<String>
    suspend fun addPost(
        body:RequestBody): Response<ResponseBody?>
    suspend fun getPosts():Response<List<Post>>

    suspend fun addComment(
       comment: Comment
    ): Response<String>

    suspend fun addReact(
        react: React
    ): Response<String>
    fun getInfo()

}