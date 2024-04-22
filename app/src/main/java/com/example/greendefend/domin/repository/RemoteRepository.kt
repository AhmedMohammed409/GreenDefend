package com.example.greendefend.date.repository

import android.net.Uri
import com.example.greendefend.date.local.account.Confirm
import com.example.greendefend.date.local.account.Login
import com.example.greendefend.date.local.account.ResponseLogin
import com.example.greendefend.date.local.account.User
import com.example.greendefend.date.local.forum.Comment
import okhttp3.ResponseBody
import retrofit2.Response

interface RemoteRepository {


    suspend fun register(user: User):Response<String>
 
    suspend fun editProfile(
        id: String,
        fullName: String,
        bio: String,
        country: String,
        fileUri: Uri,
        fileRealPath: String
    ): Response<ResponseBody>

    suspend fun addPost(
        id: String,
        postValue: String,
        fileUri: Uri,
        fileRealPath: String
    ): Response<ResponseBody>
    suspend fun addImage(
        id: String,
        fileUri: Uri,
        fileRealPath: String
    ): Response<ResponseBody>

    suspend fun login(login: Login):Response<ResponseLogin>

    suspend fun confirmAccount(confirm: Confirm):Response<String>


    suspend fun AddComment(
       comment: Comment
    ): Response<String>

    suspend fun AddReact(
        comment: Comment
    ): Response<String>
    fun getInfo()

}