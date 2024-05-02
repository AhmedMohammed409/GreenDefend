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
import com.example.greendefend.databinding.FragmentSignupBinding
import com.example.greendefend.domin.model.account.ErrorRegister
import com.example.greendefend.domin.model.account.User
import com.example.greendefend.domin.useCase.AuthViewModel
import com.example.greendefend.utli.NetworkResult
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignupFragment : Fragment() {
    private lateinit var binding: FragmentSignupBinding
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
        binding = FragmentSignupBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnSend.setOnClickListener {
            binding.progressBar.visibility = View.VISIBLE
            signUpAndObserve(
                User(
                    binding.etName.text.toString(),
                    binding.etEmail.text.toString(),
                    binding.etPassword.text.toString(),
                    binding.etConfirm.text.toString()
                )
            )
        }

    }

    private fun validationPassword(password: String, confirm: String): Boolean {
        if (password.length < 8) {
            binding.TextInputLayoutPassword.error =
                "please enter Correct password contain number and symbols and minimum 8 max 24 "
            return false
        } else if (password != confirm) {
            binding.TextInputLayoutConfirm.error = "please enter Confirm same Password"
            return false
        }
        binding.TextInputLayoutPassword.isErrorEnabled = false
        binding.TextInputLayoutConfirm.isErrorEnabled = false
        return true
    }

    private fun signUpAndObserve(user: User) {
        viewModelAccount.signup(user)
        viewModelAccount.response.observe(viewLifecycleOwner) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(requireContext(), "Sucessfull", Toast.LENGTH_SHORT).show()
                    Log.e("result", response.data.toString())
                    viewModelAccount.rest()
                    findNavController().navigate(SignupFragmentDirections.actionSignupFragmentToEntercodeFragment(user.email.toString()))

                }

                is NetworkResult.Error -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(requireContext(), response.errMsg.toString(), Toast.LENGTH_SHORT).show()
                    Log.e("result eror", response.toString())
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

}
   

