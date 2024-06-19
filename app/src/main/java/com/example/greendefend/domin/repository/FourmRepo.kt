package com.example.greendefend.domin.repository

import androidx.lifecycle.LiveData
import com.example.greendefend.domin.model.forum.Comment
import com.example.greendefend.domin.model.forum.Post
import com.example.greendefend.domin.model.forum.React
import com.example.greendefend.utli.NetworkResult
import okhttp3.RequestBody
import okhttp3.Response

interface FourmRepo {
    suspend fun addPost(
        body: RequestBody
    ): NetworkResult<Any>
   //suspend fun getPosts(): NetworkResult<Any>
    suspend fun getPosts():retrofit2.Response<List<Post>>

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