package com.example.greendefend.ui.authentication

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.greendefend.R
import com.example.greendefend.databinding.FragmentLoginBinding
import com.example.greendefend.ui.homing.HomeActivity


class LoginFragment : Fragment() {
   private lateinit var binding: FragmentLoginBinding



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
        binding= FragmentLoginBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)




        val span=SpannableString(getString(R.string.green_defend))
        val fcBlack= ForegroundColorSpan(Color.BLACK)
        val fcGreen=ForegroundColorSpan(resources.getColor(R.color.green_name,requireContext().theme))
        val size=getString(R.string.green_defend).length
        span.setSpan(fcBlack,0,size/2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        span.setSpan(fcGreen,size/2,size,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        binding.txtAppName.text = span

        binding.btnCreate.setOnClickListener {
        findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToSignupFragment())
        }
        binding.txtForget.setOnClickListener {
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToForgetFragment())
        }

        binding.btnLogin.setOnClickListener {
            startActivity(Intent(requireActivity(),HomeActivity::class.java)) }

    }


}