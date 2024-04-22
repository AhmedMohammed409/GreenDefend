package com.example.greendefend.domin.repository

import android.net.Uri
import com.example.greendefend.domin.model.account.Confirm
import com.example.greendefend.domin.model.account.Login
import com.example.greendefend.domin.model.account.ResponseLogin
import com.example.greendefend.domin.model.account.User
import com.example.greendefend.domin.model.forum.Comment
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