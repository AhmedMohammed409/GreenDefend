package com.example.greendefend.data.remote

import com.example.greendefend.domin.model.account.Confirm
import com.example.greendefend.domin.model.account.Login
import com.example.greendefend.domin.model.account.ResponseLogin
import com.example.greendefend.domin.model.account.User
import com.example.greendefend.domin.model.forum.Comment
import com.example.greendefend.domin.model.forum.Post
import com.example.greendefend.domin.model.forum.React
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT


interface ApiServiceServer {



    @POST("Account/Login")
    suspend fun login(@Body login: Login): Response<ResponseLogin>




    @POST("Account/Confirm")
    suspend fun confirm(confirm: Confirm): Response<String>

    @POST("Account/Register")
     suspend fun signup(@Body user: User):Response<String>



     @PUT("Account/EditProfile")
     suspend fun editProfile(@Body body: RequestBody):Response<ResponseBody?>


    @POST("forum/AddPost")
    suspend fun addPost(
        @Body body: RequestBody?
    ):Response<ResponseBody?>

    @GET("forum/GetPosts")
    suspend fun getPosts():Response<List<Post>>
    @POST("forum/AddComment")
    suspend fun addComment(
       @Body comment: Comment
    ): Response<String>


    @POST("forum/AddReact")
    suspend fun addReact(
       @Body react: React
    ): Response<String>


}