package com.example.greendefend.ui.boarding


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.greendefend.date.repository.DataStoreRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DataStoreViewModel @Inject constructor(
    private val dataStoreRepositoryImpl: DataStoreRepositoryImpl
) : ViewModel() {

    private var onboardingOpenedMutable:MutableLiveData<Boolean> = MutableLiveData()
    val onboardingOpened:LiveData<Boolean>get() = onboardingOpenedMutable
    fun onGetStartedBtnClicked() {
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreRepositoryImpl.saveOnboardingOpenedState(true)
        }
    }
 suspend fun  readOnboardingOpened() = viewModelScope.launch {
        onboardingOpenedMutable.value= dataStoreRepositoryImpl.readOnboardingOpenedState()
     }






}