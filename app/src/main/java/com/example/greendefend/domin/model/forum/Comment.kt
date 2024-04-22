package com.example.greendefend.domin.model.forum

import com.google.gson.annotations.SerializedName

data class Comment(
    val comment: String,
    @SerializedName("userID")
    val userId: String,
    @SerializedName("postID")
    val postId: Long,
)
