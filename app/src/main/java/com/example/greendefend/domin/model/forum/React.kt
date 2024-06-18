package com.example.greendefend.domin.model.forum

import com.google.gson.annotations.SerializedName

data class React(
    val upOrDown: Boolean,
    @SerializedName("userID")
    val userId: String,
    @SerializedName("postID")
    val postId: Int,
)