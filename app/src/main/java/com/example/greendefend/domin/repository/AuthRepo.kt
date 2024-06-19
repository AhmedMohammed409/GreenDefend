package com.example.greendefend.domin.repository

import com.example.greendefend.domin.model.account.AddNewPassword
import com.example.greendefend.domin.model.account.ChangePassword
import com.example.greendefend.domin.model.account.Confirm
import com.example.greendefend.domin.model.account.Login
import com.example.greendefend.domin.model.account.User
import com.example.greendefend.utli.NetworkResult
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response

interface AuthRepo {
    suspend fun login(login: Login): NetworkResult<Any>
//    suspend fun logout(userID: String): NetworkResult<Any>
suspend fun logout(userID: String): NetworkResult<Any>
    suspend fun getUserData(userID: String): NetworkResult<Any>
    suspend fun confirmAccount(confirm: Confirm): NetworkResult<Any>
    suspend fun sendForgetPasswordOTP(email: String): NetworkResult<Any>
    suspend fun checkForgetPasswordOTP(email: String,code:String): NetworkResult<Any>
    suspend fun addingNewPassword(addNewPassword: AddNewPassword): NetworkResult<Any>
    suspend fun changePassword(changePassword: ChangePassword): NetworkResult<Any>
    suspend fun register(user: User): NetworkResult<Any>

    suspend fun editProfile(
        body: RequestBody
    ): NetworkResult<Any>
}