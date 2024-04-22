package com.example.greendefend.ui.authentication

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.greendefend.date.local.account.Confirm
import com.example.greendefend.date.local.account.Login
import com.example.greendefend.date.local.account.User
import com.example.greendefend.date.repository.RemoteRepositoryImp
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
                repositoryImp.connectionError.value=result.message().toString()
//                    failedMutableLiveData.postValue(result.body().toString())
            }
        }catch (ex :HttpRetryException){
            repositoryImp.connectionError.value="Internet is not connect"

        }
        catch (e:IOException)
        {
            repositoryImp.connectionError.value=e.message
        }


        }

    fun login(login: Login){

        viewModelScope.launch {
            try {
                val result=  repositoryImp.login(login)

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
            }catch (ex :Exception){
                repositoryImp.connectionError.value=ex.message

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
            }catch (ex :Exception){
                repositoryImp.connectionError.value=ex.message

            }

        }

    }


    }

//    suspend fun editProfile(id: String,
//                            fullName: String,
//                            bio: String,
//                            country: String,
//                            fileUri: Uri
//    ){
//       repositoryImp.editProfile(id, fullName, bio, country, fileUri)
//    }




