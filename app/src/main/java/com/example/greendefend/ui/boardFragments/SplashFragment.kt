package com.example.greendefend.ui.boardFragments

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.greendefend.R
import com.example.greendefend.R.color.green_name
import com.example.greendefend.databinding.FragmentSplashBinding


class SplashFragment : Fragment() {
 private lateinit var binding: FragmentSplashBinding
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
        binding= FragmentSplashBinding.inflate(inflater,container,false)
        return binding.root
    }

    @SuppressLint("ResourceAsColor")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val text= getString(R.string.green_defend)
        val span= SpannableString(text)
        val fcGreen=ForegroundColorSpan(green_name)
        val fcBlack=ForegroundColorSpan(Color.BLACK)
        span.setSpan(fcBlack,0,6,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        span.setSpan(fcGreen,6,12,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        binding.txtGreendefend.text = span


    }

}