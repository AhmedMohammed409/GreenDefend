package com.example.greendefend.remote

import retrofit2.http.POST

interface ApiServiceMachineLearning {
    @POST
    suspend fun uploadImage()
}