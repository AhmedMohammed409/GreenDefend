package com.example.greendefend.ui.authentication

import android.content.Intent
import android.os.Bundle
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
import com.example.greendefend.databinding.FragmentLoginBinding
import com.example.greendefend.domin.model.account.Login
import com.example.greendefend.domin.useCase.AuthViewModel
import com.example.greendefend.ui.homing.HomeActivity
import com.example.greendefend.utli.NetworkResult
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
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
binding.progressBar.visibility=View.VISIBLE
            loginAndObserve(
                Login(
                    binding.etEmail.text.toString(),
                    binding.etPassword.text.toString()
                )
            )
        }

    }

    private fun loginAndObserve(login: Login) {
       lifecycleScope.launch {
           when(val response=viewModelAccount.login(login)){
               is NetworkResult.Success->{
                   binding.progressBar.visibility=View.GONE
                   startActivity(Intent(requireActivity(),HomeActivity::class.java))
                   requireActivity().finish()
               }
               is NetworkResult.Error->{
                   binding.progressBar.visibility=View.GONE
                   Toast.makeText(requireContext(),response.errorMsg,Toast.LENGTH_LONG).show()
               }
               is NetworkResult.Exception->{
                   binding.progressBar.visibility=View.GONE
                   Toast.makeText(requireContext(),response.e.message,Toast.LENGTH_LONG).show()
                   response.e.printStackTrace()
               }

           }

       }

//        viewModelAccount.connectionError.observe(viewLifecycleOwner) {
//            if (it.isNotEmpty()) {
//                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
//                binding.progressBar.visibility = View.GONE
//                viewModelAccount.rest()
//            }
//        }
//        viewModelAccount.serverResponse.observe(viewLifecycleOwner) {
//            if (it.isNotEmpty()) {
//                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
//                binding.progressBar.visibility = View.GONE
//                viewModelAccount.rest()
//            startActivity(Intent(requireActivity(),HomeActivity::class.java))
//                requireActivity().finish()
//
//
//            }
//        }


    }


}



