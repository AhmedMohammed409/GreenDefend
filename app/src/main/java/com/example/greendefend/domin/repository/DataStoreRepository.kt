package com.example.greendefend.domin.repository

import kotlinx.coroutines.flow.Flow

interface DataStoreRepository {
    suspend fun saveOnboardingOpenedState(isOpened: Boolean)

   suspend fun readOnboardingOpenedState():Boolean?


}