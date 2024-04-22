package com.example.greendefend.date.repository

import android.content.Context
import android.net.Uri
import androidx.lifecycle.MutableLiveData
import com.example.greendefend.date.local.account.Confirm
import com.example.greendefend.date.local.account.Login
import com.example.greendefend.date.local.account.ResponseLogin
import com.example.greendefend.date.local.account.User
import com.example.greendefend.date.local.forum.Comment
import com.example.greendefend.date.local.weather.CurrentWeather
import com.example.greendefend.date.remote.ApiServiceServer
import com.example.greendefend.date.remote.ApiServiceWeather
import dagger.hilt.android.qualifiers.ActivityContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Response
import java.io.File
import javax.inject.Inject


class RemoteRepositoryImp @Inject constructor(
    @ActivityContext private var ctx:Context,
    private var apiServiceServer: ApiServiceServer,
    private var apiServiceWeather: ApiServiceWeather


) : RemoteRepository {

    var connectionError=MutableLiveData("")
    var serverResponse=MutableLiveData("")

    fun rest(){
         connectionError.value=""
         serverResponse.value=""
    }

    suspend fun getCurrentWeather(
        key: String,
        location: String,
        days: Int,
        date: String,
        lang: String
    ):Response<CurrentWeather> { return apiServiceWeather.getCurrentWeather(key, location, days, date, lang)}




    override suspend fun register(user: User):Response<String>{
        return apiServiceServer.signup(user)
    }

    override suspend fun editProfile(
        id: String,
        fullName: String,
        bio: String,
        country: String,
        fileUri: Uri,
        fileRealPath: String
    ): Response<ResponseBody> {

        val fileToSend = prepareFilePart("key", fileRealPath,fileUri)
        val idRequestBody = id.toResponseBody("text/plain".toMediaTypeOrNull())
        val fullNameRequestBody = fullName.toResponseBody("text/plain".toMediaTypeOrNull())
        val bioRequestBody = bio.toResponseBody("text/plain".toMediaTypeOrNull())
        val countryRequestBody = country.toResponseBody("text/plain".toMediaTypeOrNull())

       return apiServiceServer.editProfile(idRequestBody,fullNameRequestBody,bioRequestBody,countryRequestBody,fileToSend)


    }

    override suspend fun addPost(
        id: String,
        postValue: String,
        fileUri: Uri,
        fileRealPath: String
    ): Response<ResponseBody> {
       apiServiceServer.addPost()
    }

    override suspend fun login(login: Login): Response<ResponseLogin> =apiServiceServer.login(login)


    override suspend fun confirmAccount(confirm: Confirm): Response<String> =apiServiceServer.confirm(confirm)

    override suspend fun AddImage(
        id: String,
        fileUri: Uri,
        fileRealPath: String
    ): Response<ResponseBody> {
        TODO("Not yet implemented")
    }

    override suspend fun AddComment(comment: Comment): Response<String> {
        TODO("Not yet implemented")
    }

    override suspend fun AddReact(comment: Comment): Response<String> {
        TODO("Not yet implemented")
    }

    private fun prepareFilePart(partName: String,fileRealPath: String,fileUri: Uri): MultipartBody.Part {
        val file =File(fileRealPath)
        val requestFile: RequestBody =
            file.asRequestBody(ctx.contentResolver.getType(fileUri)!!.toMediaTypeOrNull())
        return MultipartBody.Part.createFormData(partName,file.name,requestFile)


    }





    override fun getInfo() {
        TODO("Not yet implemented")
    }


}