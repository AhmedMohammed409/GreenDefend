package com.example.greendefend.loginFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.greendefend.databinding.FragmentForgetBinding


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


}