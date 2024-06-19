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
            NetworkResult.Success(response.code(), response.body()!!)

        }catch (e:HttpRetryException){
            NetworkResult.Error( e.responseCode(),"Server is not response")
        }
        catch (e: HttpException) {
            NetworkResult.Error(e.code(),e.message())
        }catch (e: IOException) {
            Log.e("cause 404",e.message.toString()+e.cause)
            e.printStackTrace()
            NetworkResult.Error( 404,"Not Connect Internet ")

        }catch (e: Exception) {
            NetworkResult.Error(700,"Not Authentication")
        }

        catch (e: Throwable) {
            NetworkResult.Exception(e)
        }
    }
}