package com.example.greendefend.data.repository

import com.example.greendefend.data.remote.ApiServiceServer
import com.example.greendefend.data.remote.ApiServiceWeather
import com.example.greendefend.domin.model.account.AddNewPassword
import com.example.greendefend.domin.model.account.ChangePassword
import com.example.greendefend.domin.model.account.Confirm
import com.example.greendefend.domin.model.account.Login
import com.example.greendefend.domin.model.account.User
import com.example.greendefend.domin.model.forum.Comment
import com.example.greendefend.domin.model.forum.React
import com.example.greendefend.domin.repository.AuthRepo
import com.example.greendefend.domin.repository.ClassficationRepo
import com.example.greendefend.domin.repository.FourmRepo
import com.example.greendefend.domin.repository.WeatherRepo
import com.example.greendefend.utli.ApiHandler
import com.example.greendefend.utli.NetworkResult
import okhttp3.RequestBody
import javax.inject.Inject


class RemoteRepositoryImp @Inject constructor(
    private var apiServiceServer: ApiServiceServer,
    private var apiServiceWeather: ApiServiceWeather


) : WeatherRepo, ApiHandler,AuthRepo,FourmRepo,ClassficationRepo {


    //Account
    override suspend fun register(user: User) = handleApi { apiServiceServer.signup(user) }
    override suspend fun login(login: Login) = handleApi { apiServiceServer.login(login) }
    override suspend fun logout(userID: String) = handleApi {
        apiServiceServer.logout(userID)
    }

    override suspend fun getUserData(userID: String) =
        handleApi { apiServiceServer.getUserData(userID) }

    override suspend fun confirmAccount(confirm: Confirm) =
        handleApi { apiServiceServer.confirm(confirm) }

    override suspend fun sendForgetPasswordOTP(email: String) =
        handleApi { apiServiceServer.sendForgetPasswordOTP(email) }

    override suspend fun checkForgetPasswordOTP(email: String, code: String) =
        handleApi { apiServiceServer.checkForgetPasswordOTP(email, code) }

    override suspend fun addingNewPassword(addNewPassword: AddNewPassword) =
        handleApi { apiServiceServer.addingNewPassword(addNewPassword) }

    override suspend fun changePassword(changePassword: ChangePassword) =
        handleApi { apiServiceServer.changePassword(changePassword) }

    override suspend fun editProfile(body: RequestBody) =
        handleApi { apiServiceServer.editProfile(body) }

    //Fourm
    override suspend fun addPost(body: RequestBody) = handleApi { apiServiceServer.addPost(body) }
    override suspend fun getPosts() = handleApi { apiServiceServer.getPosts() }


    override suspend fun addComment(comment: Comment) =
        handleApi { apiServiceServer.addComment(comment) }


    override suspend fun addReact(react: React) = handleApi { apiServiceServer.addReact(react) }
    override suspend fun getPostDetail(id: Int) = handleApi { apiServiceServer.getPostDetail(id) }


    //Weather
    override suspend fun getWeather(
        lat: Float,
        lon: Float,
        key: String,
        lang: String
    ): NetworkResult<Any> = handleApi { apiServiceWeather.getWeather(lat, lon, key, lang) }

    //Classification
    override suspend fun addImage(body: RequestBody) =
        handleApi { apiServiceServer.addImage(body) }

    override suspend fun getImageDetaild(imageid: Int, userId: String) = handleApi {
        apiServiceServer.getImageDetaild(imageid, userId) }

}




