package com.example.greendefend.domin.useCase.viewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.greendefend.Constants
import com.example.greendefend.data.repository.RemoteRepositoryImp
import com.example.greendefend.domin.model.weather.AllWeather
import com.example.greendefend.utli.Info
import com.example.greendefend.utli.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class WeatherViewModel @Inject constructor(private var remoteRepositoryImp: RemoteRepositoryImp) :
    ViewModel() {
    private var responseMutableLiveData = MutableLiveData<NetworkResult<Any>>()
    val response: MutableLiveData<NetworkResult<Any>> get() = responseMutableLiveData
    fun getWeather(latitude: Float, longitude: Float){
        viewModelScope.launch {
            val info = Info()
            responseMutableLiveData.postValue(remoteRepositoryImp.getWeather(
                latitude,
                longitude,
                Constants.KEY,
               info.getLanguage()
            )
            )
        }
    }
}