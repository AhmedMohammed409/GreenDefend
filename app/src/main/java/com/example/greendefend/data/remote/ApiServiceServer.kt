package com.example.greendefend.date.remote

import com.example.greendefend.domin.model.account.Confirm
import com.example.greendefend.domin.model.account.Login
import com.example.greendefend.domin.model.account.ResponseLogin
import com.example.greendefend.domin.model.account.User
import com.example.greendefend.domin.model.forum.Comment
import com.example.greendefend.domin.model.forum.React
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part


interface ApiServiceServer {


    @Headers("Content-Type:application/json")
    @POST("Account/Login")
    suspend fun login(@Body login: Login): Response<ResponseLogin>


    @Headers("Content-Type:application/json")
    @POST("Account/Confirm")
    suspend fun confirm(confirm: Confirm): Response<String>
    @Headers("Content-Type:application/json,accept:application/json")
    @POST("Account/Register")
     suspend fun signup(@Body user: User):Response<String>


     @Headers("Content-Type:multipart/form-data")
     @Multipart
     @PUT("Account/EditProfile")
     suspend fun editProfile(
         @Part("id") id: ResponseBody,
         @Part("FullName") fullName: ResponseBody,
         @Part("Bio") bio: ResponseBody,
         @Part("Country") country: ResponseBody,
         @Part file: MultipartBody.Part?
     ):Response<ResponseBody>

    @Headers("Content-Type:multipart/form-data,accept:application/json")
    @Multipart
    @POST("Account/EditProfile")
    suspend fun addPost(
        @Part("UserId") id: ResponseBody,
        @Part("PostValue") postValue: ResponseBody,
        @Part file: MultipartBody.Part?
    ):Response<ResponseBody>


    @Headers("Content-Type:application/json,accept:application/json")
    @POST("forum/AddComment")
    suspend fun addComment(
       @Body comment: Comment
    ): Response<String>


    @Headers("Content-Type:application/json,accept:application/json")
    @POST("forum/AddReact")
    suspend fun addReact(
       @Body react: React
    ): Response<String>


}