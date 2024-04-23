package com.example.greendefend.domin.useCase

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.greendefend.data.repository.RemoteRepositoryImp
import com.example.greendefend.domin.model.account.Confirm
import com.example.greendefend.domin.model.account.Login
import com.example.greendefend.domin.model.account.User
import com.example.greendefend.domin.model.forum.Comment
import com.example.greendefend.domin.model.forum.React
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.IOException
import java.net.HttpRetryException
import javax.inject.Inject


@HiltViewModel
class ViewModelAccount @Inject constructor(private var repositoryImp: RemoteRepositoryImp) :
    ViewModel() {
    private val _fileName = MutableLiveData("")
    val fileName: LiveData<String>
        get() = _fileName

    // new added
    fun setFileName(name:String) {
        _fileName.value = name
    }
    fun rest(){
        repositoryImp.rest()
    }
    val serverResponse: LiveData<String> get() = repositoryImp.serverResponse
    val connectionError: LiveData<String> get() = repositoryImp.connectionError



    fun signup(user: User) = viewModelScope.launch {
        try {
            val result=  repositoryImp.register(user)

            if (result.isSuccessful){
//                    loginMutableLiveData.postValue(result.body())
                repositoryImp.serverResponse.value=result.message()
            }
            else{
                repositoryImp.connectionError.value=result.body().toString()
//                    failedMutableLiveData.postValue(result.body().toString())
            }

        }  catch (e:HttpRetryException){
            repositoryImp.connectionError.value="Server Not Response "
            e.printStackTrace()
        }
        catch (e:IOException)
        {
            e.printStackTrace()
            repositoryImp.connectionError.value="Internet is not connect"
        }


        }

    fun login(login: Login){

        viewModelScope.launch {
            try {
                val result=  repositoryImp.login(login)

                if (result.isSuccessful){
                    repositoryImp.serverResponse.value=result.message()
                    Log.i("Id", result.body().toString())
                }
                else{
                    repositoryImp.connectionError.value="Password is irrcorect"
                }
            }
            catch (e:IOException)
            {
                repositoryImp.connectionError.value="Internet is not connect"
            }
            catch (e:HttpRetryException){
                repositoryImp.connectionError.value="Server Not Response "
                e.printStackTrace()
            }catch (e :Exception){
                repositoryImp.connectionError.value=e.message

            }

        }

    }
    fun confirm(confirm: Confirm){

        viewModelScope.launch {
            try {
                val result=  repositoryImp.confirmAccount(confirm)

                if (result.isSuccessful){
                    repositoryImp.serverResponse.value=result.message()
                }
                else{
                    repositoryImp.connectionError.value="Password is irrcorect"
                }
            }
            catch (e:IOException)
            {
                repositoryImp.connectionError.value="Internet is not connect"
            } catch (e:HttpRetryException){
                repositoryImp.connectionError.value="Server Not Response "
                e.printStackTrace()
            }catch (ex :Exception){
                repositoryImp.connectionError.value=ex.message

            }

        }

    }

         fun addPost(id: String,
                            postValue: String,
                            fileUri: Uri,
                            fileRealPath: String

    )=viewModelScope.launch {

             viewModelScope.launch {
                 try {
                     val result=  repositoryImp.addPost(id, postValue,fileUri,fileRealPath)

                     if (result.isSuccessful){
                         repositoryImp.serverResponse.value=result.message()
                     }
                     else{
                         repositoryImp.connectionError.value="Password is irrcorect"
                     }
                 }
                 catch (e:IOException)
                 {
                     e.printStackTrace()
                     repositoryImp.connectionError.value="Internet is not connect"
                 }
                 catch (e:HttpRetryException){
                     repositoryImp.connectionError.value="Server Not Response "
                     e.printStackTrace()
                 }
                 catch (ex :Exception){
                     repositoryImp.connectionError.value=ex.message

                 }

             }

        }
     suspend fun addComment(comment: Comment) {
         viewModelScope.launch {
             try {
                 val result=  repositoryImp.addComment(comment)

                 if (result.isSuccessful){
                     repositoryImp.serverResponse.value=result.message()
                 }
                 else{
                     repositoryImp.connectionError.value="Password is irrcorect"
                 }
             }
             catch (e:IOException)
             {
                 e.printStackTrace()
                 repositoryImp.connectionError.value="Internet is not connect"
             }
             catch (e:HttpRetryException){
                 repositoryImp.connectionError.value="Server Not Response "
                 e.printStackTrace()
             }
             catch (ex :Exception){
                 repositoryImp.connectionError.value=ex.message

             }

         }

    }

     suspend fun addReact(react: React){
         viewModelScope.launch {
             try {
                 val result=  repositoryImp.addReact(react)

                 if (result.isSuccessful){
                     repositoryImp.serverResponse.value=result.message()
                 }
                 else{
                     repositoryImp.connectionError.value="Password is irrcorect"
                 }
             }
             catch (e:IOException)
             {
                 e.printStackTrace()
                 repositoryImp.connectionError.value="Internet is not connect"
             }
             catch (e:HttpRetryException){
                 repositoryImp.connectionError.value="Server Not Response "
                 e.printStackTrace()
             }
             catch (ex :Exception){
                 repositoryImp.connectionError.value=ex.message

             }

         }
    }
     fun editProfile(id: String,
                            fullName: String,
                            bio: String,
                            country: String,
                            fileUri: Uri,
                            fileRealPath: String
    ){
        viewModelScope.launch {
            try {
                val result=  repositoryImp.editProfile(id, fullName,bio,country,fileUri,fileRealPath)

                if (result.isSuccessful){
                    repositoryImp.serverResponse.value=result.message()
                }
                else{
                    repositoryImp.connectionError.value=result.errorBody().toString()
                }
            }
            catch (e:IOException)
            {
                repositoryImp.connectionError.value="Internet is not connect"
            }
            catch (e:HttpRetryException){
                repositoryImp.connectionError.value="Server Not Response "
                e.printStackTrace()
            }catch (ex :Exception){
                repositoryImp.connectionError.value=ex.message

            }

        }
    }




    }







