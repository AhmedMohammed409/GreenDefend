package com.example.greendefend.domin.model.account

import java.io.Serializable

data class UserData(
    val bio: String,
    val country: String,
    val email: String,
    val fullName: String,
    val imageUrl: String,
    val userId: String
):Serializable