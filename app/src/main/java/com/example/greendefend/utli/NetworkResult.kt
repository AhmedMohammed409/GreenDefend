package com.example.greendefend.utli

sealed class NetworkResult<out T : Any> {
     data class Success<out T : Any>(val code: Int, val data: T) : NetworkResult<T>()
     data class Error( val errMsg:String) : NetworkResult<String>()
     data class Exception(val e: Throwable) : NetworkResult<Nothing>()
}