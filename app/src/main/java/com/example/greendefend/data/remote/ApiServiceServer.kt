package com.example.greendefend.data.remote

import com.example.greendefend.domin.model.account.AddNewPassword
import com.example.greendefend.domin.model.account.ChangePassword
import com.example.greendefend.domin.model.account.Confirm
import com.example.greendefend.domin.model.account.Login
import com.example.greendefend.domin.model.account.ResponseLogin
import com.example.greendefend.domin.model.account.User
import com.example.greendefend.domin.model.account.UserData
import com.example.greendefend.domin.model.classification.GetImageDeatailed
import com.example.greendefend.domin.model.forum.Comment
import com.example.greendefend.domin.model.forum.DetailPost
import com.example.greendefend.domin.model.forum.Post
import com.example.greendefend.domin.model.forum.React
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface ApiServiceServer {


    //Authentication
    @POST("Account/Register")
    suspend fun signup(@Body user: User): Response<String>
    @POST("Account/ConfirmAccount")
    suspend fun confirm(@Body confirm: Confirm): Response<String>
    @POST("Account/Login")
    suspend fun login(@Body login: Login): Response<ResponseLogin>
    @POST("Account/LogOut")
    suspend fun logout(@Query("userID") userID: String): Response<ResponseBody>

    @GET("Account/GetUserData")
    suspend fun getUserData(@Query("userId") userId: String): Response<UserData>
    @POST("Account/SendForgetPasswordOTP")
    suspend fun sendForgetPasswordOTP(@Query("Email") email: String): Response<ResponseBody>
    @POST("Account/CheckForgetPasswordOTP")
    suspend fun checkForgetPasswordOTP(
        @Query("Email") email: String,
        @Query("Code") code: String
    ): Response<ResponseBody>

    @POST("Account/AddingNewPassword")
    suspend fun addingNewPassword(@Body addNewPassword: AddNewPassword): Response<ResponseBody>
    @POST("Account/ChangePassword")
    suspend fun changePassword(@Body changePassword: ChangePassword): Response<ResponseBody>

    @PUT("Account/EditProfile")
    suspend fun editProfile(@Body body: RequestBody): Response<ResponseBody>


    //fourm

    @POST("forum/AddPost")
    suspend fun addPost(
        @Body body: RequestBody
    ): Response<ResponseBody>

    @GET("forum/GetPosts")
    suspend fun getPosts(): Response<List<Post>>

    @POST("forum/AddComment")
    suspend fun addComment(
        @Body comment: Comment
    ): Response<String>


    @POST("forum/AddReact")
    suspend fun addReact(
        @Body react: React
    ): Response<String>

    @POST("forum/GetPostDetail")
    suspend fun getPostDetail(
        @Query("PostId") id:Int
    ): Response<DetailPost>


    //Classification
    @POST("Classfication/AddImage")
    suspend fun addImage(
        @Body body: RequestBody
    ): Response<ResponseBody>
    @GET("Classfication/GetImageDetaild")
    suspend fun getImageDetaild(@Query("ImageID") imageId: Int, @Query("Key") userId:String): Response<GetImageDeatailed>


}