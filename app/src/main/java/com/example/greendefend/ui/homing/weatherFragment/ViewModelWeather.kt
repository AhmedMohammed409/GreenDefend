package com.example.greendefend.ui.homing.weatherFragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.greendefend.Constants
import com.example.greendefend.model.weather.Weather
import com.example.greendefend.repository.RemoteRepositoryImp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject


@HiltViewModel
class ViewModelWeather @Inject constructor(private var remoteRepositoryImp: RemoteRepositoryImp):ViewModel() {

    private val currentTime=System.currentTimeMillis()
    private val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
    private val date= formatter.format(currentTime)!!
    private val lang= Locale.getDefault().language

      var weatherLiveData=MutableLiveData<Response<Weather>>()

    fun getWeather(latitude:Float,longitude:Float){
        viewModelScope.launch {
            val result=remoteRepositoryImp.getWeather(Constants.key,"$latitude,$longitude",1,date,lang)
            weatherLiveData.value=result
        }
    }

}