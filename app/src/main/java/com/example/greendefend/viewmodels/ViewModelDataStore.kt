package com.example.greendefend.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.greendefend.date.repository.LocalRepositoryImp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ViewModelDataStore @Inject constructor(private val localRepositoryImp: LocalRepositoryImp):ViewModel(){

    private var valueMutable=MutableLiveData<String>()
    val value:LiveData<String> get() = valueMutable
    fun save(key:String,value:String){
        viewModelScope.launch {
            localRepositoryImp.save(key,value)
        }
    }
    fun get(key: String)= viewModelScope.launch {
      valueMutable.value= localRepositoryImp.get(key)
    }

}