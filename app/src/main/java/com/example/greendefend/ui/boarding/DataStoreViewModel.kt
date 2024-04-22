package com.example.greendefend.ui.boarding


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.greendefend.domin.useCase.SaveOnboardingStateUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val saveOnboardingStateUseCase: SaveOnboardingStateUseCase
) : ViewModel() {

    fun onGetStartedBtnClicked() {
        viewModelScope.launch(Dispatchers.IO) {
            saveOnboardingStateUseCase(true)
        }
    }





}