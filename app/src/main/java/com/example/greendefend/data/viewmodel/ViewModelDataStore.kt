package com.example.greendefend.data.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.greendefend.repository.LocalRepositoryImp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ViewModelDataStore @Inject constructor(private val localRepositoryImp: LocalRepositoryImp):ViewModel(){
    fun save(key:String,value:String){
        viewModelScope.launch {
            localRepositoryImp.save(key,value)
        }
    }
    fun get(key: String)=
        viewModelScope.launch {
            localRepositoryImp.get(key) }

}