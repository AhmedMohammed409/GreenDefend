package com.example.greendefend.domin.model.forum

import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class Post (

    @SerializedName("postId"        ) var postId        : Int?    = null,
    @SerializedName("postValue"     ) var postValue     : String? = null,
    @SerializedName("postImageURL"  ) var postImageURL  : String? = null,
    @SerializedName("userName"      ) var userName      : String? = null,
    @SerializedName("userImageURL"  ) var userImageURL  : String? = null,
    @SerializedName("createdAt"     ) var createdAt     : String? = null,
    @SerializedName("commentsCount" ) var commentsCount : Int?    = null,
    @SerializedName("likeStatus"    ) var likeStatus    : String? = null

): Serializable
