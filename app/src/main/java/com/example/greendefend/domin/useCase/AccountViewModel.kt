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
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.IOException
import java.net.HttpRetryException
import javax.inject.Inject


@HiltViewModel
class AccountViewModel @Inject constructor(
    private var repositoryImp: RemoteRepositoryImp,
    private var addSkillUseCase: AddSkillUseCase
) :
    ViewModel() {
    private val _fileName = MutableLiveData("")
    val fileName: LiveData<String>
        get() = _fileName

    // new added
    fun setFileName(name: String) {
        _fileName.value = name
    }

    fun rest() {
        repositoryImp.rest()
    }

    val serverResponse: LiveData<String> get() = repositoryImp.serverResponse
    val connectionError: LiveData<String> get() = repositoryImp.connectionError


    fun signup(user: User) = viewModelScope.launch {
        try {
            val result = repositoryImp.register(user)

            if (result.isSuccessful) {
                repositoryImp.serverResponse.value = result.message()
            } else {
                repositoryImp.connectionError.value = "Email Already Registered"

            }

        } catch (e: HttpRetryException) {
            repositoryImp.connectionError.value = "Server Not Response "
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
            repositoryImp.connectionError.value = "Internet is not connect"
        }


    }

    fun login(login: Login) {

        viewModelScope.launch {
            try {
                val result = repositoryImp.login(login)

                if (result.isSuccessful) {
                    repositoryImp.serverResponse.value = result.message()
                    Log.i("Id", result.body().toString())
                } else {
                    repositoryImp.connectionError.value = "Password is irrcorect"
                }
            } catch (e: IOException) {
                repositoryImp.connectionError.value = "Internet is not connect"
            } catch (e: HttpRetryException) {
                repositoryImp.connectionError.value = "Server Not Response "
                e.printStackTrace()
            } catch (e: Exception) {
                repositoryImp.connectionError.value = e.message

            }

        }

    }

    fun confirm(confirm: Confirm) {

        viewModelScope.launch {
            try {
                val result = repositoryImp.confirmAccount(confirm)

                if (result.isSuccessful) {
                    repositoryImp.serverResponse.value = result.message()
                } else {
                    repositoryImp.connectionError.value = "Password is irrcorect"
                }
            } catch (e: IOException) {
                repositoryImp.connectionError.value = "Internet is not connect"
            } catch (e: HttpRetryException) {
                repositoryImp.connectionError.value = "Server Not Response "
                e.printStackTrace()
            } catch (ex: Exception) {
                repositoryImp.connectionError.value = ex.message

            }

        }

    }


    fun editProfile(
        id: String,
        fullName: String,
        bio: String,
        country: String,
        imageUri: Uri
    ) {
        viewModelScope.launch {
            try {
                val multipart = addSkillUseCase.invoke(id, fullName, bio, country, imageUri)

                val result=repositoryImp.editProfile(multipart)

                if (result.isSuccessful) {
                    repositoryImp.serverResponse.value = result.message()
                } else {
                    Log.e(
                        "msgErr",
                        result.errorBody().toString() + "\n massage ${result.message()}}"
                    )
                    repositoryImp.connectionError.value = result.errorBody().toString()
                }
            } catch (e: IOException) {
                repositoryImp.connectionError.value = "Internet is not connect"
            } catch (e: HttpRetryException) {
                repositoryImp.connectionError.value = "Server Not Response "
                e.printStackTrace()
            } catch (ex: Exception) {
                repositoryImp.connectionError.value = ex.message

            }

        }


    }


}







