package com.example.greendefend.utli

import com.example.greendefend.data.local.Converters
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

interface ApiHandler {
    suspend fun <T : Any> handleApi(
        execute: suspend () -> Response<T>
    ): NetworkResult<Any> {
        return try {
            val response = execute()
            if (response.isSuccessful) {
                NetworkResult.Success(response.code(), response.body()!!)
            } else {
                NetworkResult.Error(response.errorBody().toString())
            }
        } catch (e: HttpException) {
            NetworkResult.Error( e.message())
        }catch (e: IOException) {
            NetworkResult.Error( "Not Connect Internet ")
        }catch (e: Exception) {
            NetworkResult.Exception(e)
        }catch (e: Throwable) {
            NetworkResult.Exception(e)
        }
    }
}