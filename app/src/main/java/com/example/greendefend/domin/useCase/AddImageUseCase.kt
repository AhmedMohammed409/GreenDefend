package com.example.greendefend.domin.useCase

import android.content.Context
import android.net.Uri
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject
class AddImageUseCase @Inject constructor(private val context: Context) {
    operator fun invoke(
        imageUri: Uri
    ): MultipartBody {
        val multipart = MultipartBody.Builder().setType(MultipartBody.FORM)
        val fileRealPath=getFilePathFromUri(context,imageUri)
        val file=File(fileRealPath)
        multipart.apply {
            addFormDataPart(
                name = "Image",
                filename = file.name,
                body = file.asRequestBody("image/*".toMediaType())
            )
        }

        return multipart.build()
    }


}