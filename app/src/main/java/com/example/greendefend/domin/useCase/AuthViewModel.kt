package com.example.greendefend.domin.useCase

import android.net.Uri
import android.util.Log
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.greendefend.Constants
import com.example.greendefend.data.repository.DataStorePrefrenceImpl
import com.example.greendefend.data.repository.RemoteRepositoryImp
import com.example.greendefend.domin.model.account.Confirm
import com.example.greendefend.domin.model.account.Login
import com.example.greendefend.domin.model.account.ResponseLogin
import com.example.greendefend.domin.model.account.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.IOException
import java.net.HttpRetryException
import javax.inject.Inject


@HiltViewModel
class AccountViewModel @Inject constructor(
    private var  addSkillUseCase: AddSkillUseCase,
    private var dataStorePrefrenceImpl: DataStorePrefrenceImpl,
    private var repositoryImp: RemoteRepositoryImp
) :
    ViewModel() {



    fun rest() {
        repositoryImp.rest()
    }

    val serverResponse: LiveData<String> get() = repositoryImp.serverResponse
    val connectionError: LiveData<String> get() = repositoryImp.connectionError


    private fun saveInDataStore(responseLogin: ResponseLogin) {
        viewModelScope.launch{
            dataStorePrefrenceImpl.putPreference(
                DataStorePrefrenceImpl.Token_KEY,
                responseLogin.token!!
            )
            dataStorePrefrenceImpl.putPreference(
                DataStorePrefrenceImpl.Name_KEY,
                responseLogin.token!!
            )
            dataStorePrefrenceImpl.putPreference(
                DataStorePrefrenceImpl.Email_KEY,
                responseLogin.email!!
            )
            dataStorePrefrenceImpl.putPreference(
                DataStorePrefrenceImpl.Country_KEY,
                responseLogin.region!!
            )
            dataStorePrefrenceImpl.putPreference(
                DataStorePrefrenceImpl.Bio_KEY,
                responseLogin.message!!
            )
            dataStorePrefrenceImpl.putPreference(
                booleanPreferencesKey("isAuthenticated"),
                responseLogin.isAuthenticated!!
            )
            dataStorePrefrenceImpl.putPreference(
                stringPreferencesKey("userId"),
                responseLogin.userId!!
            )

        }
    }

    fun signup(user: User) = viewModelScope.launch {
        try {
            val result = repositoryImp.register(user)

            if (result.isSuccessful) {
                repositoryImp.serverResponse.value = result.message()
            } else {
                repositoryImp.connectionError.value = "Email Already Registered"

            }

        } catch (e: HttpRetryException) {
            repositoryImp.connectionError.value = "Server Not Response "
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
            repositoryImp.connectionError.value = "Internet is not connect"
        }


    }

    fun login(login: Login) {
        viewModelScope.launch {
            try {
                val result = repositoryImp.login(login)
                if (result.isSuccessful) {
                  if (result.body()!=null){
                      repositoryImp.serverResponse.value = result.message()
                      Constants.Token = result.body()!!.token!!
                      Constants.Id=result.body()!!.userId!!
                  }

                } else {
                    repositoryImp.connectionError.value = "Password is irrcorect"
                }
            } catch (e: IOException) {
                repositoryImp.connectionError.value = "Internet is not connect"
            } catch (e: HttpRetryException) {
                repositoryImp.connectionError.value = "Server Not Response "
                e.printStackTrace()
            } catch (e: Exception) {
                repositoryImp.connectionError.value = e.message

            }
        }


    }

    fun confirm(confirm: Confirm) {
        viewModelScope.launch {
            try {
                val result = repositoryImp.confirmAccount(confirm)

                if (result.isSuccessful) {
                    repositoryImp.serverResponse.value = result.message()
                } else {
                    repositoryImp.connectionError.value = "Password is irrcorect"
                }
            } catch (e: IOException) {
                repositoryImp.connectionError.value = "Internet is not connect"
            } catch (e: HttpRetryException) {
                repositoryImp.connectionError.value = "Server Not Response "
                e.printStackTrace()
            } catch (ex: Exception) {
                repositoryImp.connectionError.value = ex.message

            }

        }

    }
    fun edit( id: String,
              fullName: String,
              bio: String,
              country: String,
              imageUri: Uri?){
        viewModelScope.launch {
            try {
                val multipart=addSkillUseCase.invoke(id, fullName, bio, country, imageUri)
                val result=repositoryImp.editProfile(multipart)
                if (result.isSuccessful){
                    repositoryImp.serverResponse.value="Sucessfull"
                    Log.e("MS Error",result.body().toString()+result.message()+result.errorBody())
                }else{
                    Log.e("MS Error",result.body().toString()+result.message()+result.errorBody())
                    repositoryImp.serverResponse.value="failed"
                }
            }catch (e:IOException){
                repositoryImp.serverResponse.value="Not connection"
            }catch (e:HttpRetryException){
                repositoryImp.serverResponse.value="server not response"
            }catch (e:Exception){
                e.printStackTrace()
            }

        }
    }



}



