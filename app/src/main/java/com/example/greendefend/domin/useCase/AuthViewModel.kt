package com.example.greendefend.domin.useCase

import android.net.Uri
import android.util.Log
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.greendefend.data.repository.DataStorePrefrenceImpl
import com.example.greendefend.data.repository.RemoteRepositoryImp
import com.example.greendefend.domin.model.account.Confirm
import com.example.greendefend.domin.model.account.Login
import com.example.greendefend.domin.model.account.ResponseLogin
import com.example.greendefend.domin.model.account.User
import com.example.greendefend.utli.ApiHandler
import com.example.greendefend.utli.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.IOException
import java.net.HttpRetryException
import javax.inject.Inject


@HiltViewModel
class AuthViewModel @Inject constructor(
    private var addSkillUseCase: EditProfileUseCase,
    private var dataStorePrefrenceImpl: DataStorePrefrenceImpl,
    private var repositoryImp: RemoteRepositoryImp
) :
    ViewModel() ,ApiHandler{

    private var connectionErrorMutable = MutableLiveData<String>()
    private var serverResponseMutable = MutableLiveData<String>()

    private var responseMutableLiveData = MutableLiveData<NetworkResult<Any>>()
    val responseLiveData: LiveData<NetworkResult<Any>> get() = responseMutableLiveData
    val serverResponse: LiveData<String> get() = serverResponseMutable
    val connectionError: LiveData<String> get() = connectionErrorMutable

    fun rest() {
        connectionErrorMutable.value = ""
        serverResponseMutable.value = ""
    }

    private fun saveInDataStore(responseLogin: ResponseLogin) {
        viewModelScope.launch {
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
                serverResponseMutable.value = result.message()
            } else {
                connectionErrorMutable.value = "Email Already Registered"

            }

        } catch (e: HttpRetryException) {
            connectionErrorMutable.value = "Server Not Response "
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
            connectionErrorMutable.value = "Internet is not connect"
        }

    }


         suspend fun login(login: Login)=handleApi { repositoryImp.login(login) }


    fun confirm(confirm: Confirm) {
        viewModelScope.launch {
            try {
                val result = repositoryImp.confirmAccount(confirm)

                if (result.isSuccessful) {
                  serverResponseMutable.value = result.message()
                } else {
                 connectionErrorMutable.value = "Password is irrcorect"
                }
            } catch (e: IOException) {
               connectionErrorMutable.value = "Internet is not connect"
            } catch (e: HttpRetryException) {
               connectionErrorMutable.value = "Server Not Response "
                e.printStackTrace()
            } catch (ex: Exception) {
              connectionErrorMutable.value = ex.message

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
                  serverResponseMutable.value="Sucessfull"
                    Log.e("MS Error",result.body().toString()+result.message()+result.errorBody())
                }else{
                    Log.e("MS Error",result.body().toString()+result.message()+result.errorBody())
                    serverResponseMutable.value="failed"
                }
            }catch (e:IOException){
               serverResponseMutable.value="Not connection"
            }catch (e:HttpRetryException){
               serverResponseMutable.value="server not response"
            }catch (e:Exception){
                e.printStackTrace()
            }

        }
    }



}


/*fun login(login: Login) {

      viewModelScope.launch {
          try {
              val result = repositoryImp.login(login)
              if (result.isSuccessful) {
                  if (result.body()!=null){
//                        saveInDataStore(result.body()!!)
                      serverResponseMutable.value = result.message()
                      Constants.Token = result.body()!!.token!!
                      Constants.Id=result.body()!!.userId!!
                  }

              } else if (!result.isSuccessful) {
                  connectionErrorMutable.value = "Password is irrcorect"
              }
          } catch (e: IOException) {
              connectionErrorMutable.value = "Internet is not connect"
          } catch (e: HttpRetryException) {
              connectionErrorMutable.value = "Server Not Response "
              e.printStackTrace()
          } catch (e: Exception) {
              connectionErrorMutable.value = e.message

          }
      }
  }*/
