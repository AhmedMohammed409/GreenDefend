package com.example.greendefend.domin.useCase.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.greendefend.Constants
import com.example.greendefend.domin.model.weather.CurrentWeather
import com.example.greendefend.data.repository.RemoteRepositoryImp
import com.example.greendefend.utli.Info
import com.example.greendefend.utli.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okio.IOException
import java.net.HttpRetryException
import javax.inject.Inject


@HiltViewModel
class WeatherViewModel @Inject constructor(private var remoteRepositoryImp: RemoteRepositoryImp) :
    ViewModel() {
    private var responseMutableLiveData = MutableLiveData<NetworkResult<Any>>()
    val response: MutableLiveData<NetworkResult<Any>> get() = responseMutableLiveData
    fun getCurrentWeather(latitude: Float, longitude: Float){
        viewModelScope.launch {
            val info = Info()
            responseMutableLiveData.postValue(remoteRepositoryImp.getCurrentWeather(
                Constants.KEY,
                "$latitude,$longitude",
                1,
                info.getDate(),
                info.getLanguage()))
        }
    }


}