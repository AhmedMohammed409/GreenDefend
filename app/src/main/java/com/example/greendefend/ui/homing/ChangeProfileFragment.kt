package com.example.greendefend.ui.homing

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.greendefend.databinding.FragmentChangeprofileBinding


class ChangeProfileFragment : Fragment() {
private lateinit var binding: FragmentChangeprofileBinding

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
        binding= FragmentChangeprofileBinding.inflate(inflater,container,false)
        return binding.root
    }


}