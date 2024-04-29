package com.example.greendefend.domin.useCase

import android.content.Context
import android.net.Uri
import com.example.greendefend.utli.ConvertUriToFile
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import javax.inject.Inject

class AddPostUseCase @Inject constructor(private val context: Context) {
    operator fun invoke(
        id: String,
        postValue: String,
        imageUri: Uri
    ): MultipartBody {
        val multipart = MultipartBody.Builder().setType(MultipartBody.FORM)
        val file = ConvertUriToFile.uriToFile(imageUri, context.contentResolver)
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