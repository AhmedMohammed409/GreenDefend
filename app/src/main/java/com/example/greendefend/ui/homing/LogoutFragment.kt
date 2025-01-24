package com.example.greendefend.ui.homing

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.greendefend.Constants
import com.example.greendefend.data.repository.DataStorePrefrenceImpl
import com.example.greendefend.databinding.FragmentLogoutBinding
import com.example.greendefend.domin.useCase.viewModels.AuthViewModel
import com.example.greendefend.ui.authentication.AuthenticationActivity
import com.example.greendefend.utli.NetworkResult
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class LogoutFragment : Fragment() {
private lateinit var binding: FragmentLogoutBinding
private val authViewModel:AuthViewModel by viewModels()
    @Inject
   lateinit var dataStorePrefrenceImpl:DataStorePrefrenceImpl
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

        binding= FragmentLogoutBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.progressBar.visibility=View.VISIBLE
        logoutAndObserve()
    }

    private fun logoutAndObserve() {
        authViewModel.logout(Constants.Id)
        authViewModel.response.observe(viewLifecycleOwner) { response ->
          binding.progressBar.visibility=View.GONE
            when (response) {
                is NetworkResult.Success -> {
                    lifecycleScope.launch {
                        dataStorePrefrenceImpl.clearAllPreference()
                        dataStorePrefrenceImpl.putPreference(
                            DataStorePrefrenceImpl.onboardingOpenedState_Key, true
                        )
                    }
                    startActivity(Intent(requireContext(), AuthenticationActivity::class.java))


                }

                is NetworkResult.Error -> {
                    Log.e("error msg logout", response.errMsg + response.code)
                    findNavController().navigateUp()
                    Toast.makeText(requireActivity(), "Failed Logout", Toast.LENGTH_SHORT).show()
                }


            }
        }
    }


}