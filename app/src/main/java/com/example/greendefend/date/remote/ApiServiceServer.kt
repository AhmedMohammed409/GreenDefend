package com.example.greendefend.date.remote

import com.example.greendefend.date.local.account.Confirm
import com.example.greendefend.date.local.account.Login
import com.example.greendefend.date.local.account.ResponseLogin
import com.example.greendefend.date.local.account.User
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part


interface ApiServiceServer {


    @POST("Account/Login")
    suspend fun login(@Body login: Login): Response<ResponseLogin>

    @POST("Account/Confirm")
    suspend fun confirm(confirm: Confirm): Response<String>
//
//      @Headers(
//          "accept:Application/json",
//          "Content-Type: Application/json"
//      )
      @POST("Account/Register")
     suspend fun signup(@Body user: User):Response<String>


     @Headers("Content-Type:multipart/form-data")
     @Multipart
     @POST("Account/EditProfile")
     suspend fun editProfile(
         @Part("id") id: ResponseBody,
         @Part("FullName") fullName: ResponseBody,
         @Part("Bio") bio: ResponseBody,
         @Part("Country") country: ResponseBody,
         @Part file: MultipartBody.Part?
     ):Response<ResponseBody>

    @Headers("Content-Type:multipart/form-data")
    @Multipart
    @POST("Account/EditProfile")
    suspend fun addPost(
        @Part("UserId") id: ResponseBody,
        @Part("PostValue") postValue: ResponseBody,
        @Part file: MultipartBody.Part?
    ):Response<ResponseBody>

    @GET("Account/Login")
    suspend fun getInfo(): Response<String>
}