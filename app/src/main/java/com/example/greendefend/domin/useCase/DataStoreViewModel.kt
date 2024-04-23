package com.example.greendefend.ui.boarding


import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.greendefend.date.repository.DataStoreRepositoryImpl
import dagger.hilt.android.internal.Contexts.getApplication
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DataStoreViewModel @Inject constructor(
    private val dataStoreRepositoryImpl: DataStoreRepositoryImpl,application: Application
) : AndroidViewModel(application){


     val readOnboardingOpened=dataStoreRepositoryImpl.readOnboardingOpenedState().asLiveData(application)

    fun onGetStartedBtnClicked() {
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreRepositoryImpl.saveOnboardingOpenedState(true)
        }

    }

//    private suspend fun readOnboardingOpened(): Flow<Boolean> {
//      val result= viewModelScope.async {
//            dataStoreRepositoryImpl.readOnboardingOpenedState()
//        }
//        return result.await()
//    }







}