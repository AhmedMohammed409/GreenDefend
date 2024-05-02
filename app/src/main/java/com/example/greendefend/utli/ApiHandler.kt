package com.example.greendefend.utli

import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

interface ApiHandler {

    suspend fun <T : Any> handleApi(
        execute: suspend () -> Response<T>
    ): NetworkResult<Any> {
        val response = execute()
        return try {
            if (response.isSuccessful) {
                NetworkResult.Success(response.code(), response.body()!!)
            } else {
                NetworkResult.Error(response.code(),response.errorBody().toString())
            }
        } catch (e: HttpException) {
            NetworkResult.Error(response.code(),response.message())
        }catch (e: IOException) {
            NetworkResult.Error( response.code(),"Not Connect Internet ")
        }catch (e: Exception) {
            NetworkResult.Exception(e)
        }catch (e: Throwable) {
            NetworkResult.Exception(e)
        }
    }
}