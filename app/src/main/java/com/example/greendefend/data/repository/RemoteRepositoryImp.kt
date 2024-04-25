package com.example.greendefend.data.repository

import android.content.Context
import android.net.Uri
import androidx.lifecycle.MutableLiveData
import com.example.greendefend.data.remote.ApiServiceServer
import com.example.greendefend.data.remote.ApiServiceWeather
import com.example.greendefend.domin.model.account.Confirm
import com.example.greendefend.domin.model.account.Login
import com.example.greendefend.domin.model.account.ResponseLogin
import com.example.greendefend.domin.model.account.User
import com.example.greendefend.domin.model.forum.Comment
import com.example.greendefend.domin.model.forum.React
import com.example.greendefend.domin.model.weather.CurrentWeather
import com.example.greendefend.domin.repository.RemoteRepository
import dagger.hilt.android.qualifiers.ActivityContext
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
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
    private var dataStorePrefrenceImpl: DataStorePrefrenceImpl,
    private var apiServiceServer: ApiServiceServer,
    private var apiServiceWeather: ApiServiceWeather


) : RemoteRepository {

    var connectionError=MutableLiveData("")
    var serverResponse=MutableLiveData("")

    fun rest(){
         connectionError.value=""
         serverResponse.value=""
    }
    suspend fun addResult(responseLogin: ResponseLogin){
            dataStorePrefrenceImpl.putPreference(DataStorePrefrenceImpl.Token_KEY, responseLogin.token!!)
            dataStorePrefrenceImpl.putPreference(DataStorePrefrenceImpl.Name_KEY, responseLogin.fullName!!)
            dataStorePrefrenceImpl.putPreference(DataStorePrefrenceImpl.Email_KEY, responseLogin.email!!)
            dataStorePrefrenceImpl.putPreference(DataStorePrefrenceImpl.Country_KEY, responseLogin.region!!)
            dataStorePrefrenceImpl.putPreference(DataStorePrefrenceImpl.Bio_KEY, responseLogin.message!!)

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

    override suspend fun editProfile(body: RequestBody?): Response<ResponseBody?> {
       return apiServiceServer.editProfile(body!!) }

    override suspend fun addPost(
        id: String,
        postValue: String,
        fileUri: Uri,
        fileRealPath: String
    ): Response<ResponseBody> {
        val fileToSend = prepareFilePart("PostImage", fileRealPath,fileUri)
        val idRequestBody = id.toResponseBody("text/plain".toMediaTypeOrNull())
        val postRequestBody = postValue.toResponseBody("text/plain".toMediaTypeOrNull())
        return apiServiceServer.addPost(idRequestBody,postRequestBody,fileToSend)
    }

    override suspend fun login(login: Login): Response<ResponseLogin> =apiServiceServer.login(login)


    override suspend fun confirmAccount(confirm: Confirm): Response<String> =apiServiceServer.confirm(confirm)
    override suspend fun addComment(comment: Comment): Response<String> {
       return apiServiceServer.addComment(comment)
    }

    override suspend fun addReact(react: React): Response<String> {
      return apiServiceServer.addReact(react)
    }

    override suspend fun addImage(
        id: String,
        fileUri: Uri,
        fileRealPath: String
    ): Response<ResponseBody> {
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