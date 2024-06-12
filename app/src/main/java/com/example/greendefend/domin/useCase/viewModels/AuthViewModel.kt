package com.example.greendefend.domin.useCase.viewModels

import android.annotation.SuppressLint
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.greendefend.data.repository.RemoteRepositoryImp
import com.example.greendefend.domin.model.account.AddNewPassword
import com.example.greendefend.domin.model.account.ChangePassword
import com.example.greendefend.domin.model.account.Confirm
import com.example.greendefend.domin.model.account.Login
import com.example.greendefend.domin.model.account.User
import com.example.greendefend.domin.useCase.EditProfileUseCase
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



    private var responseMutableLiveData = MutableLiveData<NetworkResult<Any>>()

    val response: LiveData<NetworkResult<Any>> get() = responseMutableLiveData

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
    fun sendForgetPasswordOtp(email:String) {
        viewModelScope.launch {
            responseMutableLiveData.postValue(repositoryImp.sendForgetPasswordOTP(email))
        }
    }

    fun checkForgetPasswordOtp(email:String,code:String) {
        viewModelScope.launch {
            responseMutableLiveData.postValue(repositoryImp.checkForgetPasswordOTP(email,code))
        }
    }
    fun addingNewPassword(addNewPassword: AddNewPassword) {
        viewModelScope.launch {
            responseMutableLiveData.postValue(repositoryImp.addingNewPassword(addNewPassword))
        }
    }
    fun changePassword(changePassword: ChangePassword) {
        viewModelScope.launch {
            responseMutableLiveData.postValue(repositoryImp.changePassword(changePassword))
        }
    }
    fun login(login: Login) {
        viewModelScope.launch {
            responseMutableLiveData.postValue(repositoryImp.login(login))
        }
    }

    fun logout(userId:String) {
        viewModelScope.launch {
            responseMutableLiveData.postValue(repositoryImp.logout(userId))
        }
    }
    fun getUserData(userId:String) {
        viewModelScope.launch {
            responseMutableLiveData.postValue(repositoryImp.getUserData(userId))
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



