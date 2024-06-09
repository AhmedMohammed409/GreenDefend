package com.example.greendefend.ui.authentication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.greendefend.Constants
import com.example.greendefend.R
import com.example.greendefend.databinding.FragmentVerificationBinding
import com.example.greendefend.domin.useCase.AuthViewModel
import com.example.greendefend.utli.NetworkResult
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class VerificationFragment : Fragment() {
    private lateinit var binding: FragmentVerificationBinding
    private val args:VerificationFragmentArgs by navArgs()
    private val viewModelAccount: AuthViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {}
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding=FragmentVerificationBinding.inflate(inflater,container,false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.txtAppName.text=Constants.provideProjectName(requireContext())
        val email=args.email
        binding.btnSend.setOnClickListener {
            val code=binding.etCode.text
            if (code!!.isNotEmpty()){
                binding.progressBar.visibility = View.VISIBLE
                verificationAndObserve(email, code.toString())
            }else{
                Toast.makeText(requireContext(),
                    getString(R.string.please_enter_code),Toast.LENGTH_SHORT).show()
            }
              }
    }
    private fun verificationAndObserve(email: String,code:String) {
        viewModelAccount.checkForgetPasswordOtp(email, code)

        viewModelAccount.response.observe(viewLifecycleOwner) { response ->
            binding.progressBar.visibility = View.GONE
            when (response) {
                is NetworkResult.Success -> {
                    findNavController().navigate(VerificationFragmentDirections.actionVerificationFragmentToUpdatePasswordFragment(email))                }
                is NetworkResult.Error -> {
                    Toast.makeText(
                        requireContext(),
                        response.errMsg.toString(),
                        Toast.LENGTH_LONG
                    ).show()
                }

                is NetworkResult.Exception -> {
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