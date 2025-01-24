package com.example.greendefend.domin.model.account

import android.net.Uri
import java.io.Serializable

data class UserData(
    var bio: String,
    var country: String,
    val email: String,
    val fullName: String,
    var imageUrl: Uri,
    val userId: String
):Serializable