package com.example.greendefend.domin.useCase.viewModels

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.greendefend.data.repository.RemoteRepositoryImp
import com.example.greendefend.domin.model.forum.Comment
import com.example.greendefend.domin.model.forum.React
import com.example.greendefend.domin.useCase.AddPostUseCase
import com.example.greendefend.utli.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForumViewModel @Inject constructor(
    private var addPost: AddPostUseCase,
    private val remoteRepositoryImp: RemoteRepositoryImp
) : ViewModel() {


    private var responseMutableLiveData = MutableLiveData<NetworkResult<Any>>()
    val response: LiveData<NetworkResult<Any>> get() = responseMutableLiveData

      val liveDataReact:LiveData<NetworkResult<Any>> get() = mutableLiveDataReact
         private  var mutableLiveDataReact=MutableLiveData<NetworkResult<Any>>()


    fun addPost(
        id: String,
        postValue: String,
        fileUri: Uri?,

        ) {
        viewModelScope.launch {
            val multipart = addPost.invoke(id, postValue, fileUri!!)
            responseMutableLiveData.postValue(remoteRepositoryImp.addPost(multipart))
        }
    }

    fun getPosts() {
        viewModelScope.launch { responseMutableLiveData.postValue(remoteRepositoryImp.getPosts()) }
    }



    fun addComment(comment: Comment) {
        viewModelScope.launch {
            responseMutableLiveData.postValue(
                remoteRepositoryImp.addComment(
                    comment
                )
            )
        }
    }


     fun addReact(react: React) {
        viewModelScope.launch {
            mutableLiveDataReact.postValue(remoteRepositoryImp.addReact(react))
        }
    }

     fun getPostDetail(id: Int){
        viewModelScope.launch {
            responseMutableLiveData.postValue(
                remoteRepositoryImp.getPostDetail(id)
            )
        }
    }
}