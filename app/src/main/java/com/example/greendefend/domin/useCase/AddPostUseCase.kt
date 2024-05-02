package com.example.greendefend.domin.useCase

import android.content.Context
import android.net.Uri
import android.util.Log
import com.example.greendefend.Constants
import com.example.greendefend.utli.ConvertUriToFile
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject

class AddPostUseCase @Inject constructor(private val context: Context) {
    operator fun invoke(
        id: String,
        postValue: String,
        imageUri: Uri
    ): MultipartBody {
        val multipart = MultipartBody.Builder().setType(MultipartBody.FORM)

       val fileRealpath=getFilePathFromUri(context,imageUri)
        val file=File(fileRealpath)
        multipart.apply {
            addFormDataPart("UserId", id)
            addFormDataPart("PostValue", postValue)
            addFormDataPart(
                name = "PostImage",
                filename = file.name,
                body = file.asRequestBody("image/*".toMediaType())
            )
        }
        return multipart.build()
    }


}