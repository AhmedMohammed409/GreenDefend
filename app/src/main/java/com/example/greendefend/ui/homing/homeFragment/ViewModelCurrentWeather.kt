package com.example.greendefend.ui.homing.homeFragment

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.greendefend.Constants
import com.example.greendefend.model.weather.CurrentWeather
import com.example.greendefend.repository.RemoteRepositoryImp
import com.example.greendefend.utli.Info
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ViewModelCurrentWeather @Inject constructor(private var remoteRepositoryImp: RemoteRepositoryImp) :
    ViewModel() {



        private var currentMutableLiveData=MutableLiveData<CurrentWeather>()
    val currentLiveData:LiveData<CurrentWeather> get() = currentMutableLiveData
    fun getCurrentWeather(latitude: Float, longitude: Float){
        val info = Info()
        viewModelScope.launch {

          val result= remoteRepositoryImp.getCurrentWeather(
                Constants.key,
                "$latitude,$longitude",
                1,
                info.getDate(),
                info.getLanguage())

            if (result.isSuccessful){
                currentMutableLiveData.value=result.body()
            }
            else{
                Log.i("MSG error",result.message())
            }

        }
    }

}