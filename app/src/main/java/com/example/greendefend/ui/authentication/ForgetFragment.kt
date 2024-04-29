package com.example.greendefend.ui.authentication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.greendefend.Constants
import com.example.greendefend.databinding.FragmentForgetBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ForgetFragment : Fragment() {
  private lateinit var binding: FragmentForgetBinding
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
            if (checkEmail(binding.etEmail.text.toString())){
            findNavController().navigate(ForgetFragmentDirections.actionForgetFragmentToVerificationFragment())}
            else{
            Toast.makeText(requireContext(),"Please Enter Correct Email",Toast.LENGTH_SHORT).show()}
        }
    }
private fun checkEmail(email:String):Boolean{
    return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()&&email.isNotEmpty()
}

}