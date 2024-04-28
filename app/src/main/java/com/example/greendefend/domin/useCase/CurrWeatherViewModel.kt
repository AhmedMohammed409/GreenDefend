package com.example.greendefend.ui.homing.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.greendefend.Constants
import com.example.greendefend.domin.model.weather.CurrentWeather
import com.example.greendefend.data.repository.RemoteRepositoryImp
import com.example.greendefend.utli.Info
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okio.IOException
import javax.inject.Inject


@HiltViewModel
class ViewModelCurrentWeather @Inject constructor(private var remoteRepositoryImp: RemoteRepositoryImp) :
    ViewModel() {


    fun rest(){
        remoteRepositoryImp.rest()
    }
    val serverResponse: LiveData<String> get() = remoteRepositoryImp.serverResponse
    val connectionError: LiveData<String> get() = remoteRepositoryImp.connectionError

        private val resultMutableLiveData=MutableLiveData<CurrentWeather>()
    val resultLiveData:LiveData<CurrentWeather> get() = resultMutableLiveData
    fun getCurrentWeather(latitude: Float, longitude: Float)=viewModelScope.launch {

        try {

            val info = Info()
            val result=remoteRepositoryImp.getCurrentWeather(
                Constants.KEY,
                "$latitude,$longitude",
                1,
                info.getDate(),
                info.getLanguage())
            if (result.isSuccessful){
                if (result.body()!=null){
                    resultMutableLiveData.value=result.body()
                    remoteRepositoryImp.serverResponse.value="Sucessfull"
                }
                else{
                    remoteRepositoryImp.connectionError.value=result.message()
                }
            }
        }catch (e :IOException){
            remoteRepositoryImp.connectionError.value="Sucessfull"
        }catch (e:Exception){
            remoteRepositoryImp.connectionError.value=e.message
        }




    }



}