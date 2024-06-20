package com.example.greendefend.domin.repository

import android.media.session.MediaSession.Token
import android.net.Uri
import com.example.greendefend.utli.NetworkResult
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response

interface ClassficationRepo {
    suspend fun addImage(
        body: RequestBody
    ): NetworkResult<Any>
   suspend fun getImageDetaild(imageid: Int,userId:String): NetworkResult<Any>
}