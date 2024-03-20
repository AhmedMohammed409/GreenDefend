package com.example.greendefend.data.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.greendefend.model.RegisterData
import com.example.greendefend.repository.RemoteRepositoryImp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class ViewModelServer @Inject constructor(private var remoteRepositoryImp: RemoteRepositoryImp):ViewModel() {
    private var loginLiveData= MutableLiveData<Response<RegisterData>>()

    fun validateSignup(firstName: String,
                       middleName: String,
                       lastName: String,
                       email: String,
                       number: String,
                       dateOfBirth: String,
                       gender: String, password: String){
        viewModelScope.launch {
           val result= remoteRepositoryImp.signup(firstName,
                middleName,
                lastName,
                email,
                number,
                dateOfBirth,
                gender, password)
            loginLiveData.value=result
        }

    }

}