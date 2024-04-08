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
import com.example.greendefend.viewmodels.ViewModelServer
import com.example.greendefend.databinding.FragmentSignupBinding


class SignupFragment : Fragment(){
    private lateinit var binding: FragmentSignupBinding
//     private val viewModelServer: ViewModelServer by lazy {
//        ViewModelProvider(requireParentFragment())[ViewModelServer::class.java] }

    private val viewModelServer: ViewModelServer by viewModels()

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
            validationAll()
        }

    }
//    validationName(binding.etName.text.toString()) &&
    private fun validationAll() {
        if (
            validationPassword(binding.etPassword.text.toString(),binding.etConfirm.text.toString())
        ) {

            observe(binding.etName.text.toString(),
            binding.etEmail.text.toString(),
            binding.etPassword.text.toString(),
            binding.etConfirm.text.toString()
            )
        }

    }

//    private fun validationName(name: String): Boolean {
//        return !(name.contains("[0-9]".toRegex()) || name.isEmpty())
//    }
    private fun validationPassword(password: String,confirm:String): Boolean {
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


private fun observe(name: String, email:String, password: String, confirm: String){
    viewModelServer.register(name,email,password,confirm)
    viewModelServer.result.observe(viewLifecycleOwner){
        if (it.isSuccessful){

            Log.i("rsponse","Registerition Sucessfull")
            Toast.makeText(requireContext(),"Registerition Sucessfull",Toast.LENGTH_LONG).show()
            findNavController().navigate(SignupFragmentDirections.actionSignupFragmentToEntercodeFragment())

        }else
        {
            Log.i("rsponse","Registerition Failed")
            Toast.makeText(requireContext(),"Registerition Failed",Toast.LENGTH_LONG).show()
        }
    }
}


}
   

