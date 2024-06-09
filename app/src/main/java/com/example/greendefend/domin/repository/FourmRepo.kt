package com.example.greendefend.domin.repository

import com.example.greendefend.domin.model.forum.Comment
import com.example.greendefend.domin.model.forum.React
import com.example.greendefend.utli.NetworkResult
import okhttp3.RequestBody

interface FourmRepo {
    suspend fun addPost(
        body: RequestBody
    ): NetworkResult<Any>
    suspend fun getPosts(): NetworkResult<Any>

    suspend fun addComment(
        comment: Comment
    ): NetworkResult<Any>

    suspend fun addReact(
        react: React
    ): NetworkResult<Any>

    suspend fun getPostDetail(
        id:Int
    ): NetworkResult<Any>
}