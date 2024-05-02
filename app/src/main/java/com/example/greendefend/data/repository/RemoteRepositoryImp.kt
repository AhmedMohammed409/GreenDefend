package com.example.greendefend.data.repository

import android.net.Uri
import com.example.greendefend.data.remote.ApiServiceServer
import com.example.greendefend.data.remote.ApiServiceWeather
import com.example.greendefend.domin.model.account.Confirm
import com.example.greendefend.domin.model.account.Login
import com.example.greendefend.domin.model.account.User
import com.example.greendefend.domin.model.forum.Comment
import com.example.greendefend.domin.model.forum.React
import com.example.greendefend.domin.model.weather.CurrentWeather
import com.example.greendefend.domin.repository.RemoteRepository
import com.example.greendefend.utli.ApiHandler
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import javax.inject.Inject


class RemoteRepositoryImp @Inject constructor(
    private var apiServiceServer: ApiServiceServer,
    private var apiServiceWeather: ApiServiceWeather


) : RemoteRepository,ApiHandler {


//weather

    override suspend fun getCurrentWeather(
        key: String,
        location: String,
        days: Int,
        date: String,
        lang: String
    )= handleApi{  apiServiceWeather.getCurrentWeather(key, location, days, date, lang)}


//Fourm
    override suspend fun addPost(body: RequestBody)=handleApi { apiServiceServer.addPost(body) }
    override suspend fun getPosts()=handleApi { apiServiceServer.getPosts() }

//Account
    override suspend fun register(user: User)=handleApi { apiServiceServer.signup(user) }
    override suspend fun login(login: Login) =handleApi { apiServiceServer.login(login)  }

    override suspend fun confirmAccount(confirm: Confirm) =handleApi { apiServiceServer.confirm(confirm) }

    override suspend fun editProfile(body: RequestBody)=handleApi { apiServiceServer.editProfile(body) }



    override suspend fun addComment(comment: Comment)=handleApi { apiServiceServer.addComment(comment) }


    override suspend fun addReact(react: React)=handleApi { apiServiceServer.addReact(react) }


    override fun getInfo() {
        TODO("Not yet implemented")
    }

    override suspend fun addImage(
        id: String,
        fileUri: Uri,
        fileRealPath: String
    ): Response<ResponseBody> {
        TODO("Not yet implemented")
    }



}