package com.example.greendefend.date.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.preferencesKey
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class LocalRepositoryImp @Inject constructor(private var dataStore: DataStore<Preferences>) {
    suspend fun save(key:String, value:String){
        val keyPref= preferencesKey<String>(key)
        dataStore.edit {
          it[keyPref]=value
        }
    }
    suspend fun get(key:String):String?{
        val keyPref= preferencesKey<String>(key)
        val prefrence=dataStore.data.first()
        return prefrence[keyPref]
    }
}