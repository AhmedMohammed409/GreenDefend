package com.example.greendefend.ui.homing.weather

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.greendefend.Constants
import com.example.greendefend.domin.model.weather.CurrentWeather
import com.example.greendefend.data.repository.RemoteRepositoryImp
import com.example.greendefend.utli.Info
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject


@HiltViewModel
class ViewModelWeather @Inject constructor(private var remoteRepositoryImp: RemoteRepositoryImp) :
    ViewModel() {

    var info = Info()
    lateinit var weather: Response<CurrentWeather>
//    fun getWeather(latitude: Float, longitude: Float):Response<CurrentWeather> {
//        viewModelScope.launch {
//            val result = remoteRepositoryImp.getCurrentWeather(
//                Constants.KEY,
//                "${latitude},${longitude}",
//                1,
//                Info().getDate(),
//                info.getLanguage()
//            )
//            weather = result
//        }
//        return  weather
//    }

}