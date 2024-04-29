package com.example.greendefend.domin.useCase

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.greendefend.data.repository.RemoteRepositoryImp
import com.example.greendefend.domin.model.forum.Comment
import com.example.greendefend.domin.model.forum.Post
import com.example.greendefend.domin.model.forum.React
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.IOException
import java.net.HttpRetryException
import javax.inject.Inject

@HiltViewModel
class ForumViewModel @Inject constructor(private var addPost: AddPostUseCase, private val remoteRepositoryImp: RemoteRepositoryImp):ViewModel() {
    private var connectionErrorMutable= MutableLiveData<String>()
    private var serverResponseMutable= MutableLiveData<String>()

    private  var postsMutableLiveData=MutableLiveData<List<Post>>()
      val posts: MutableLiveData<List<Post>> get() = postsMutableLiveData

    val serverResponse: LiveData<String> get() =serverResponseMutable
    val connectionError: LiveData<String> get() = connectionErrorMutable


    fun addPost(id: String,
                postValue: String,
                fileUri: Uri,

    )=viewModelScope.launch {
        try {
            val multipart=addPost.invoke(id,postValue,fileUri)
            val result=remoteRepositoryImp.addPost(multipart)
            Log.e("response add post",result.toString())
            if (result.isSuccessful){
                serverResponseMutable.value=result.message()
                Log.e("MS Error",result.body().toString()+result.message()+result.errorBody())
            }
        }catch (e:IOException){
            serverResponseMutable.value="Not connection"
        }catch (e:HttpRetryException){
            serverResponseMutable.value="server not response"
        }catch (e:Exception){
            serverResponseMutable.value="Failed Add Post"
            e.printStackTrace()
        }
    }

    fun getPosts()=viewModelScope.launch {
        try {
            val result=remoteRepositoryImp.getPosts()
            Log.e("response posts",result.body().toString())
            serverResponseMutable.value=result.message()
            postsMutableLiveData.value=result.body()

        }catch (e:IOException){
            serverResponseMutable.value="Not connection"
        }catch (e:HttpRetryException){
            serverResponseMutable.value="server not response"
        }catch (e:Exception){
            serverResponseMutable.value="Failed Add Post"
            e.printStackTrace()
        }
    }

    suspend fun addComment(comment: Comment) {
        viewModelScope.launch {
            try {
                val result=  remoteRepositoryImp.addComment(comment)

                if (result.isSuccessful){
                   serverResponseMutable.value=result.message()
                }
            }
            catch (e: IOException)
            {
                e.printStackTrace()
                serverResponseMutable.value="Internet is not connect"
            }
            catch (e: HttpRetryException){
                serverResponseMutable.value="Server Not Response "
                e.printStackTrace()
            }
            catch (ex :Exception){
                serverResponseMutable.value=ex.message

            }

        }

    }

    suspend fun addReact(react: React){
        viewModelScope.launch {
            try {
                val result=  remoteRepositoryImp.addReact(react)

                if (result.isSuccessful){
                    serverResponseMutable.value=result.message()
                }
                else{
                    connectionErrorMutable.value="Password is irrcorect"
                }
            }
            catch (e: IOException)
            {
                e.printStackTrace()
                connectionErrorMutable.value="Internet is not connect"
            }
            catch (e: HttpRetryException){
                connectionErrorMutable.value="Server Not Response "
                e.printStackTrace()
            }
            catch (ex :Exception){
                connectionErrorMutable.value=ex.message

            }

        }
    }


}