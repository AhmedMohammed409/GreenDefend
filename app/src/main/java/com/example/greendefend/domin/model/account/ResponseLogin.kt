package com.example.greendefend.domin.model.account

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ResponseLogin(
    @SerializedName("fullName") var fullName: String?="",
    @SerializedName("email") var email: String? ="",
    @SerializedName("userId") var userId: String? ="",
    @SerializedName("token") var token: String? ="",
    @SerializedName("region") var region: String?  ="",
    @SerializedName("message") var message: String? = "",
    @SerializedName("isAuthenticated") var isAuthenticated: Boolean? =false,
    @SerializedName("address") var address: String? = "",
    @SerializedName("expier") var expier: String? = ""
): Serializable