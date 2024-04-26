package com.example.greendefend.domin.repository

import android.net.Uri
import com.example.greendefend.domin.model.account.Confirm
import com.example.greendefend.domin.model.account.Login
import com.example.greendefend.domin.model.account.ResponseLogin
import com.example.greendefend.domin.model.account.User
import com.example.greendefend.domin.model.forum.Comment
import com.example.greendefend.domin.model.forum.React
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body

interface RemoteRepository {


    suspend fun register(user: User):Response<String>
 
    suspend fun editProfile(
       body:RequestBody?
    ): Response<ResponseBody?>

//    suspend fun addPost(
//        id: String,
//        postValue: String,
//        fileUri: Uri,
//        fileRealPath: String
//    ): Response<ResponseBody>
    suspend fun addImage(
        id: String,
        fileUri: Uri,
        fileRealPath: String
    ): Response<ResponseBody>

    suspend fun login(login: Login):Response<ResponseLogin>

    suspend fun confirmAccount(confirm: Confirm):Response<String>


    suspend fun addComment(
       comment: Comment
    ): Response<String>

    suspend fun addReact(
        react: React
    ): Response<String>
    fun getInfo()

}