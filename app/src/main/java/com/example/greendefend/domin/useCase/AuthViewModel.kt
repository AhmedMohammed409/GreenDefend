package com.example.greendefend.domin.useCase

import android.annotation.SuppressLint
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.greendefend.data.repository.RemoteRepositoryImp
import com.example.greendefend.domin.model.account.Confirm
import com.example.greendefend.domin.model.account.Login
import com.example.greendefend.domin.model.account.User
import com.example.greendefend.utli.ApiHandler
import com.example.greendefend.utli.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AuthViewModel @Inject constructor(
    private var addSkillUseCase: EditProfileUseCase,
    private var repositoryImp: RemoteRepositoryImp
) :
    ViewModel(), ApiHandler {
    private var connectionErrorMutable = MutableLiveData<Any>()
    private var serverResponseMutable = MutableLiveData<Any>()

    val serverResponse: LiveData<Any> get() = serverResponseMutable
    val connectionError: LiveData<Any> get() = connectionErrorMutable


    private var responseMutableLiveData = MutableLiveData<NetworkResult<Any>>()

    val response: MutableLiveData<NetworkResult<Any>> get() = responseMutableLiveData

    fun rest() {
        connectionErrorMutable.value = ""
        serverResponseMutable.value = ""
    }


    fun signup(user: User) {
        viewModelScope.launch {
            responseMutableLiveData.postValue(repositoryImp.register(user))
        }
    }

    fun confirm(confirm: Confirm) {
        viewModelScope.launch {
            responseMutableLiveData.postValue(repositoryImp.confirmAccount(confirm))
        }

    }

    fun login(login: Login) {
        viewModelScope.launch {
            responseMutableLiveData.postValue(repositoryImp.login(login))
        }
    }


    @SuppressLint("SuspiciousIndentation")
    fun edit(
        id: String,
        fullName: String,
        bio: String,
        country: String,
        imageUri: Uri?
    ) {
        viewModelScope.launch {
            val multipart = addSkillUseCase.invoke(id, fullName, bio, country, imageUri!!)
            responseMutableLiveData.postValue(repositoryImp.editProfile(multipart))
        }
    }


}



