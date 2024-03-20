package com.example.greendefend.remote

import com.example.greendefend.model.RegisterData
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiServiceServer {

    @GET("")
    suspend fun login():Response<String>

    @POST("register")
    @FormUrlEncoded
    @Headers("Accept:application/json")
     fun signup(
         @Field("firstName") firstName:String,
         @Field("middleName") middleName:String,
         @Field("lastName") lastName:String,
         @Field("email") email:String,
         @Field("number") number:String,
         @Field("dateOfBirth") dateOfBirth:String,
         @Field("gender") gender:String,
         @Field("password") password:String,
     ):Response<RegisterData>

    @GET("")
    suspend fun getInfo():Response<String>
}