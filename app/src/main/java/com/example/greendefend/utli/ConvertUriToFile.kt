package com.example.greendefend.utli

import android.content.ContentResolver
import android.net.Uri
import java.io.File

object ConvertUriToFile {
    fun uriToFile(uri: Uri, convertResolver: ContentResolver): File {
        val inputStream=convertResolver.openInputStream(uri)
        val file= File.createTempFile("temp",null)
        file.outputStream().use { outputStream->
            inputStream?.copyTo(outputStream)
        }
        return file
    }
}