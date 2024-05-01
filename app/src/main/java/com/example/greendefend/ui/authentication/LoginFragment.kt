package com.example.greendefend.ui.authentication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.greendefend.Constants
import com.example.greendefend.data.repository.DataStorePrefrenceImpl
import com.example.greendefend.data.repository.DataStorePrefrenceImpl.Companion.Bio_KEY
import com.example.greendefend.data.repository.DataStorePrefrenceImpl.Companion.Country_KEY
import com.example.greendefend.data.repository.DataStorePrefrenceImpl.Companion.Email_KEY
import com.example.greendefend.data.repository.DataStorePrefrenceImpl.Companion.IsAuthenticated_KEY
import com.example.greendefend.data.repository.DataStorePrefrenceImpl.Companion.Name_KEY
import com.example.greendefend.data.repository.DataStorePrefrenceImpl.Companion.Token_KEY
import com.example.greendefend.data.repository.DataStorePrefrenceImpl.Companion.userId_KEY
import com.example.greendefend.databinding.FragmentLoginBinding
import com.example.greendefend.domin.model.account.Login
import com.example.greendefend.domin.model.account.ResponseLogin
import com.example.greendefend.domin.useCase.AuthViewModel
import com.example.greendefend.ui.homing.HomeActivity
import com.example.greendefend.utli.NetworkResult
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.runBlocking
import javax.inject.Inject


@AndroidEntryPoint
class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding

    private val viewModelAccount: AuthViewModel by viewModels()

    @Inject
    lateinit var dataStorePrefrenceImpl: DataStorePrefrenceImpl
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.txtAppName.text = Constants.provideProjectName(requireActivity())
        binding.btnCreate.setOnClickListener {
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToSignupFragment())
        }
        binding.txtForget.setOnClickListener {
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToForgetFragment())
        }
        binding.btnLogin.setOnClickListener {
            binding.progressBar.visibility = View.VISIBLE
            loginAndObserve(
                Login(
                    binding.etEmail.text.toString(),
                    binding.etPassword.text.toString()
                )
            )
        }

    }

    private fun loginAndObserve(login: Login) {
        viewModelAccount.login(login)
        viewModelAccount.response.observe(viewLifecycleOwner) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(requireContext(),"Sucessfull",Toast.LENGTH_SHORT).show()
                    val responseLogin = response.data as ResponseLogin
                    runBlocking {
                        saveAtPrefrences(responseLogin)
                    }
                    Log.e("result", responseLogin.toString())
                    startActivity(Intent(requireActivity(), HomeActivity::class.java))
                    requireActivity().finish()
                }

                is NetworkResult.Error -> {
                    binding.progressBar.visibility = View.GONE
                    Log.e("result", response.toString())
                    Toast.makeText(requireContext(), response.errMsg, Toast.LENGTH_LONG)
                        .show()
                }

                is NetworkResult.Exception -> {
                    binding.progressBar.visibility = View.GONE
                    Log.e("result", response.e.toString())
                    Toast.makeText(
                        requireContext(),
                        response.e.message.toString(),
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }


    }

   private suspend fun saveAtPrefrences(responseLogin: ResponseLogin) {
        dataStorePrefrenceImpl.putPreference(
            Token_KEY,
            responseLogin.token.toString()
        )
        dataStorePrefrenceImpl.putPreference(
            Email_KEY,
            responseLogin.email.toString()
        )
        dataStorePrefrenceImpl.putPreference(
            Bio_KEY,
            responseLogin.message.toString()
        )
        dataStorePrefrenceImpl.putPreference(
            userId_KEY,
            responseLogin.userId.toString()
        )
        dataStorePrefrenceImpl.putPreference(
            Name_KEY,
            responseLogin.fullName.toString()
        )
        dataStorePrefrenceImpl.putPreference(
            Country_KEY,
            responseLogin.region.toString()
        )
        dataStorePrefrenceImpl.putPreference(
            IsAuthenticated_KEY,
            responseLogin.isAuthenticated!!
        )

    }


}



