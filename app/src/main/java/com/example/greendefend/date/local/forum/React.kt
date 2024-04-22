package com.example.greendefend.date.local.forum

import com.google.gson.annotations.SerializedName

data class React(
    val upOrDown: Boolean,
    @SerializedName("userID")
    val userId: String,
    @SerializedName("postID")
    val postId: Long,
)