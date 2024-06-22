package com.example.greendefend.utli

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
            }else{
                NetworkResult.Error(response.code(),"Failed")
            }



        }
        catch (e:HttpRetryException){
            NetworkResult.Error( e.responseCode(),"Server is not response")
        }
        catch (e: HttpException) {
            NetworkResult.Error(e.code(),e.message())
        }
        catch (e: IOException) {
            NetworkResult.Error( 600,"Not Connect Internet ")

        }
        catch (e: Exception) {
            NetworkResult.Error(700,e.cause.toString())
        }

        catch (e: Throwable) {
            NetworkResult.Exception(e)
        }
    }
}