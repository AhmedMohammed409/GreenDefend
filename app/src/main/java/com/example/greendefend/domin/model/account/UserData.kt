package com.example.greendefend.domin.model.account

import java.io.Serializable

data class UserData(
    val bio: String?=null,
    val country: String?=null,
    val email: String?=null,
    val fullName: String?=null,
    val imageUrl: String?=null,
    val userId: String?=null
):Serializable