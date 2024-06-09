package com.example.greendefend.data.repository

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.greendefend.Constants
import com.example.greendefend.domin.repository.DataStorePreference
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

private val Context.dataStore by preferencesDataStore(Constants.APP_DATA_STORE_NAME)
class DataStorePrefrenceImpl(private val context: Context) : DataStorePreference {
    companion object {
        val onboardingOpenedState_Key = booleanPreferencesKey("Onboarding_Opened_State_key")
        val Name_KEY = stringPreferencesKey("fullName")
        val Email_KEY = stringPreferencesKey("email")
        val Token_KEY = stringPreferencesKey("token")
        val Country_KEY = stringPreferencesKey("region")
        val Bio_KEY = stringPreferencesKey("Bio_KEY")
        val IsAuthenticated_KEY= booleanPreferencesKey("isAuthenticated")
        val  userId_KEY=stringPreferencesKey("userId")
        val  ImageUrl_KEY= stringPreferencesKey("imageUri")
    }

    override suspend fun <T> getPreference(key: Preferences.Key<T>, defaultValue: T):
            Flow<T> = context.dataStore.data.catch { exception ->
        if (exception is IOException) {
            emit(emptyPreferences())
        } else {
            throw exception
        }
    }.map { preferences ->
        val result = preferences[key] ?: defaultValue
        result
    }

    override suspend fun <T> putPreference(key: Preferences.Key<T>, value: T) {
        context.dataStore.edit { preferences ->
            preferences[key] = value!!
        }
    }

    override suspend fun <T> removePreference(key: Preferences.Key<T>) {
        context.dataStore.edit {
            it.remove(key)
        }
    }

    override suspend fun clearAllPreference() {
        context.dataStore.edit { preferences ->
            preferences.clear()
        }
    }




}