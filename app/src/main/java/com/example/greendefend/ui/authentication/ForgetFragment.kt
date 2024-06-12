package com.example.greendefend.ui.authentication

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
import com.example.greendefend.databinding.FragmentForgetBinding
import com.example.greendefend.domin.useCase.viewModels.AuthViewModel
import com.example.greendefend.utli.NetworkResult
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ForgetFragment : Fragment() {
  private lateinit var binding: FragmentForgetBinding
    private val authViewModel: AuthViewModel by viewModels()
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
        binding= FragmentForgetBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.txtAppName.text=Constants.provideProjectName(requireContext())



    binding.btnSend.setOnClickListener {
        val email=binding.etEmail.text.toString()

        if (checkEmail(email)){
            binding.progressBar.visibility = View.VISIBLE
            forgetAndObserve(email)
        }else{
            Toast.makeText(requireContext(),"Please check Email",Toast.LENGTH_SHORT).show()
        }


        }
    }
    private fun checkEmail(email:String):Boolean{
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()&&email.isNotEmpty()
    }

    private fun forgetAndObserve(email: String) {
        authViewModel.sendForgetPasswordOtp(email)
        authViewModel.response.observe(viewLifecycleOwner) { response ->
            binding.progressBar.visibility = View.GONE
            when (response) {
                is NetworkResult.Success -> {
                    findNavController().navigate(ForgetFragmentDirections.actionForgetFragmentToVerificationFragment(email))                }
                is NetworkResult.Error -> {
                    Toast.makeText(
                        requireContext(),
                        response.errMsg.toString(),
                        Toast.LENGTH_LONG
                    ).show()
                }

                is NetworkResult.Exception -> {
                    Log.e("error msg",response.e.message.toString())
                    Toast.makeText(
                        requireContext(),
                        response.e.message.toString(),
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }


    }

}