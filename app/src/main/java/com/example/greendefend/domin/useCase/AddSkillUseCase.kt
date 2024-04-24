package com.example.greendefend.domin.useCase

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import com.example.greendefend.data.repository.RemoteRepositoryImp
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import java.io.File
import javax.inject.Inject

class AddSkillUseCase @Inject constructor(
    private val remoteRepositoryImp: RemoteRepositoryImp,
    @ApplicationContext val context: Context) {


    suspend operator fun invoke(
        id: String,
        fullName: String,
        bio: String,
        country: String,
        imageUri:Uri
       // skillImages:List<Uri>
    ): MultipartBody {
        val multipart = MultipartBody.Builder().setType(MultipartBody.FORM)
        val file=uriToFile(imageUri,context.contentResolver)

        multipart.apply {
            addFormDataPart("id", id)
            addFormDataPart("FullName",fullName)
            addFormDataPart("Bio",bio)
            addFormDataPart("Country",country)
            addFormDataPart(name = "ProfileImage",
                filename = file.name,
                body = file.asRequestBody("image/*".toMediaType()))

//            for ( image in skillImages){
//                val file=uriToFile(image,context.contentResolver)
//            val newFile= getFilePathFromUri()
//            }
        }
return multipart.build()
    }

    private fun uriToFile(uri: Uri,convertResolver: ContentResolver): File {
        val inputStream=convertResolver.openInputStream(uri)
        val file=File.createTempFile("temp",null)
        file.outputStream().use { outputStream->
            inputStream?.copyTo(outputStream)
        }
     return file
    }

}