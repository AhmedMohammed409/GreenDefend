package com.example.greendefend.domin.model.forum

import java.io.Serializable


data class Post(

    var postId: Int? = null,
    var postValue: String? = null,
    var postImageURL: String? = null,
    var userName: String? = null,
    var userImageURL: String? = null,
    var createdAt: String? = null,
    var commentsCount: Int? = null,
    var likeStatus: String? = null

) : Serializable
