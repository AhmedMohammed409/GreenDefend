package com.example.greendefend.ui.authentication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.greendefend.databinding.ActivityAuthenticationBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class AuthenticationActivity : AppCompatActivity() {

    private lateinit var binding:ActivityAuthenticationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityAuthenticationBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }


}