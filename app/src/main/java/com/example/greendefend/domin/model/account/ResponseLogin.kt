package com.example.greendefend.date.local.account

import com.google.gson.annotations.SerializedName

data class ResponseLogin(
    @SerializedName("fullName") var fullName: String? = null,
    @SerializedName("email") var email: String? = null,
    @SerializedName("userId") var userId: String? = null,
    @SerializedName("token") var token: String? = null,
    @SerializedName("region") var region: String? = null,
    @SerializedName("message") var message: String? = null,
    @SerializedName("isAuthenticated") var isAuthenticated: Boolean? = null,
    @SerializedName("address") var address: String? = null,
    @SerializedName("expier") var expier: String? = null
)