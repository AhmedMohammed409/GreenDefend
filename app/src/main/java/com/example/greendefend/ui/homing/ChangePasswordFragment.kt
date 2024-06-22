package com.example.greendefend.ui.homing

import android.content.Context
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
import com.example.greendefend.R
import com.example.greendefend.databinding.FragmentChangePasswordBinding
import com.example.greendefend.domin.model.account.ChangePassword
import com.example.greendefend.domin.useCase.viewModels.AuthViewModel
import com.example.greendefend.utli.NetworkResult
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ChangePasswordFragment : Fragment() {
    private lateinit var binding: FragmentChangePasswordBinding
    private val authViewModel: AuthViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentChangePasswordBinding.inflate(inflater, container, false)
        binding.changePasswordFragment.txtAppName.text =
            Constants.provideProjectName(requireContext())
        binding.changePasswordFragment.etPassword.hint=requireContext().getString(R.string.enter_current_password)
        binding.changePasswordFragment.etConfirm.hint=requireContext().getString(R.string.new_password)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.changePasswordFragment.txtAppName.text =
            Constants.provideProjectName(requireContext())

        binding.changePasswordFragment.btnSend.setOnClickListener {
            binding.changePasswordFragment.progressBar.visibility = View.VISIBLE
            changePassword(
                ChangePassword(
                    binding.changePasswordFragment.etPassword.text.toString(),
                    Constants.Email,
                    binding.changePasswordFragment.etConfirm.text.toString()
                )
            )
        }


    }

    private fun changePassword(changePassword: ChangePassword) {
        authViewModel.changePassword(changePassword)
        authViewModel.response.observe(viewLifecycleOwner) { response ->
            binding.changePasswordFragment.progressBar.visibility = View.GONE
            when (response) {
                is NetworkResult.Success -> {
                    Toast.makeText(
                        requireContext(),
                        "sucessfull",
                        Toast.LENGTH_SHORT
                    ).show()
                    findNavController().navigateUp()
                }

                is NetworkResult.Error -> {

                  if (response.code==400){
                      Toast.makeText(
                          requireContext(),
                          " Incorrect current password",
                          Toast.LENGTH_SHORT
                      ).show()
                  }
                    Log.e("err masage",response.toString())

                }

                is NetworkResult.Exception -> {
                    Toast.makeText(
                        requireContext(), "exeption" +
                                response.e.message.toString(),
                        Toast.LENGTH_LONG
                    ).show()
                }

                else -> {}
            }
        }

    }

    override fun onAttach(context: Context) {
        (requireActivity() as HomeActivity).binding.toolbar.visibility=View.GONE
        super.onAttach(context)
    }

    override fun onDestroy() {
        (requireActivity() as HomeActivity).binding.toolbar.visibility=View.VISIBLE
        super.onDestroy()
    }


}