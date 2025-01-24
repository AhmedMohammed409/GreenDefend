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
import com.example.greendefend.databinding.FragmentUpdatePasswordBinding
import com.example.greendefend.domin.model.account.AddNewPassword
import com.example.greendefend.domin.useCase.viewModels.AuthViewModel
import com.example.greendefend.utli.NetworkResult
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class UpdatePasswordFragment : Fragment() {
   private lateinit var binding: FragmentUpdatePasswordBinding
   private val args:UpdatePasswordFragmentArgs by navArgs()
    private val authViewModel: AuthViewModel by viewModels ()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {}
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding= FragmentUpdatePasswordBinding.inflate(inflater,container,false)
        binding.etPassword.hint=requireContext().getString(R.string.password)
        binding.etConfirm.hint=requireContext().getString(R.string.confirm)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.txtAppName.text=Constants.provideProjectName(requireContext())
        binding.btnSend.setOnClickListener {

            val password=binding.etPassword.text.toString()
            val confirm=binding.etConfirm.text.toString()
            if (password == confirm && password.isNotEmpty()){
               binding.progressBar.visibility=View.VISIBLE
                updatePassword(AddNewPassword(args.email,password,confirm))
            }else{
                Toast.makeText(requireContext(),"Failed to Change Password",Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun updatePassword(addNewPassword: AddNewPassword){
        authViewModel.addingNewPassword(addNewPassword)
        authViewModel.response.observe(viewLifecycleOwner) { response ->
            binding.progressBar.visibility=View.GONE
            when (response) {
                is NetworkResult.Success -> {
                    findNavController().navigate(UpdatePasswordFragmentDirections.actionUpdatePasswordFragmentToLoginFragment())
                                   }
                is NetworkResult.Error -> {
                  if(response.code==600){
                      Toast.makeText(requireContext(),response.errMsg,Toast.LENGTH_SHORT).show()
                  }
                    Toast.makeText(
                        requireContext(),
                        response.errMsg.toString(),
                        Toast.LENGTH_LONG
                    ).show()
                }

            }
        }

    }


}