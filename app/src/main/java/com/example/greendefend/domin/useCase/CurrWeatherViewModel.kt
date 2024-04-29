package com.example.greendefend.domin.useCase

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
import java.net.HttpRetryException
import javax.inject.Inject


@HiltViewModel
class CurrWeatherViewModel @Inject constructor(private var remoteRepositoryImp: RemoteRepositoryImp) :
    ViewModel() {
    private var connectionErrorMutable=MutableLiveData<String>()
    private var serverResponseMutable= MutableLiveData<String>()

    val serverResponse: LiveData<String> get() =serverResponseMutable
    val connectionError: LiveData<String> get() = connectionErrorMutable



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
                   serverResponseMutable.value="Sucessfull"
                }
                else{
                   connectionErrorMutable.value=result.message()
                }
            }
        }catch (e :IOException){
           connectionErrorMutable.value="Sucessfull"
        }catch (e:HttpRetryException){
            e.printStackTrace()
           connectionErrorMutable.value="Server Not Response"
        }
        catch (e:Exception){
           connectionErrorMutable.value=e.message
        }




    }



}