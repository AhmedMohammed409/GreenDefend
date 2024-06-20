package com.example.greendefend.domin.useCase.viewModels

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.greendefend.data.repository.RemoteRepositoryImp
import com.example.greendefend.domin.useCase.AddImageUseCase
import com.example.greendefend.utli.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ClassificationViewModel @Inject constructor(
    private val addImageUseCase: AddImageUseCase,
    private val remoteRepositoryImp: RemoteRepositoryImp
) : ViewModel() {

    private var responseMutableLiveData = MutableLiveData<NetworkResult<Any>>()
    val response: LiveData<NetworkResult<Any>> get() = responseMutableLiveData


     fun addImage(imageUri: Uri) {
     viewModelScope.launch {
         responseMutableLiveData.postValue(
             remoteRepositoryImp.addImage(
                 addImageUseCase.invoke(
                     imageUri
                 )
             )
         )
     }
    }
    fun getImageDetailed(imageId:Int,userId:String){
        viewModelScope.launch {
            responseMutableLiveData.postValue(remoteRepositoryImp.getImageDetaild(imageId,userId))
        }
    }

}

