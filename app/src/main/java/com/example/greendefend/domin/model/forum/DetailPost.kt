package com.example.greendefend.domin.model.forum

data class DetailPost(
    val comments: List<CommentX>,
    val disLikesCount: Int,
    val likesCount: Int,
    val postBody: String,
    val postDate: String,
    val postId: Int,
    val postImageURL: String,
    val userCountry: String,
    val userImageURL: String,
    val userName: String
)