package com.example.greendefend

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.greendefend.databinding.ActivityMainBinding
import com.example.greendefend.ui.adapters.ViewPagerAdapter
import com.example.greendefend.ui.authenticationFragments.AuthenticationActivity
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.viewPagerFrgment.adapter= ViewPagerAdapter(this,this)

        TabLayoutMediator(binding.tablayout,binding.viewPagerFrgment){ _, _ ->}.attach()


        if(  binding.viewPagerFrgment.currentItem==2){
            binding.btnNext.text= getString(R.string.finished)
        }


        binding.btnNext.setOnClickListener {


            if (binding.viewPagerFrgment.currentItem > binding.viewPagerFrgment.childCount) {

                val intent = Intent(applicationContext, AuthenticationActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                binding.viewPagerFrgment.setCurrentItem(binding.viewPagerFrgment.currentItem + 1, true)
            }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_drawer,menu)
        return true

    }
}