package com.example.greendefend.date.local.account

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("fullName") var fullName: String? = null,
    @SerializedName("email") var email: String? = null,
    @SerializedName("password") var password: String? = null,
    @SerializedName("confirmPassword") var confirmPassword: String? = null
)
