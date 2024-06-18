package com.example.greendefend.utli

import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import java.net.HttpRetryException

interface ApiHandler {

    suspend fun <T : Any> handleApi(
        execute: suspend () -> Response<T>
    ): NetworkResult<Any> {
//        Log.e("response",response.toString())
        return try {

            val response = execute()
            if (response.isSuccessful) {
                NetworkResult.Success(response.code(), response.body()!!)
            } else {
                NetworkResult.Error(response.code(), response.errorBody()?.toString())
            }
        }catch (e:HttpRetryException){
            NetworkResult.Error( 800,"Server is not response")
        }
        catch (e: HttpException) {
            NetworkResult.Error(e.code(),e.message())
        }catch (e: IOException) {
            NetworkResult.Error( 700,"Not Connect Internet ")
        }catch (e: Exception) {
            NetworkResult.Exception(e)
        }

        catch (e: Throwable) {
            NetworkResult.Exception(e)
        }
    }
}