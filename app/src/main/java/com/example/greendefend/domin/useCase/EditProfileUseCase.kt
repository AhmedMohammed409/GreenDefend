package com.example.greendefend.domin.useCase

import android.content.Context
import android.net.Uri
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject
class EditProfileUseCase @Inject constructor(val context: Context) {
   operator fun invoke(
       id: String,
       fullName: String,
       bio: String,
       country: String,
       imageUri:Uri
       // skillImages:List<Uri>
    ): MultipartBody {
       val multipart = MultipartBody.Builder().setType(MultipartBody.FORM)
       val fileRealpath=getFilePathFromUri(context,imageUri)
       val file= File(fileRealpath)
        multipart.apply {
            addFormDataPart("id", id)
            addFormDataPart("FullName",fullName)
            addFormDataPart("Bio",bio)
            addFormDataPart("Country",country)
            addFormDataPart(
                name = "ProfileImage",
                filename = file.name,
                body = file.asRequestBody("image/*".toMediaType()))

//            for ( image in skillImages){
//                val file=uriToFile(image,context.contentResolver)
//            val newFile= getFilePathFromUri()
//            }
        }
       return multipart.build()
    }
}