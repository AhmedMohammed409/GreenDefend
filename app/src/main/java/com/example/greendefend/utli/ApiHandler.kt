package com.example.greendefend.utli

import android.util.Log
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import java.net.HttpRetryException

interface ApiHandler {

    suspend fun <T : Any?> handleApi(
        execute: suspend () -> Response<T>
    ): NetworkResult<Any> {
//        Log.e("response",response.toString())
        return try {
            val response = execute()
            if (response.isSuccessful){
                NetworkResult.Success(response.code(), response.body()!!)
            }
            else {
                Log.i("weather respone ","Failed")
                NetworkResult.Error(response.code(),"Failed")
            }
        }
        catch (e:HttpRetryException){
            NetworkResult.Error( e.responseCode(),"Server is not response")
        }
        catch (e: IOException) {
            NetworkResult.Error( 600,"Not Connect Internet ")
        }
        catch (e: HttpException) {

            NetworkResult.Error(e.code(),e.message())
        }
        catch (e: Exception) {
            NetworkResult.Error(1100,e.cause.toString())
        }

    }
}