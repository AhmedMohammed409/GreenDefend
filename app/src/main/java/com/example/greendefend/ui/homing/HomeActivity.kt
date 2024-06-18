package com.example.greendefend.ui.homing

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupWithNavController
import com.bumptech.glide.Glide
import com.example.greendefend.Constants
import com.example.greendefend.R
import com.example.greendefend.data.repository.DataStorePrefrenceImpl
import com.example.greendefend.databinding.ActivityHomeBinding
import com.example.greendefend.domin.useCase.viewModels.AuthViewModel
import com.example.greendefend.ui.authentication.AuthenticationActivity
import com.example.greendefend.utli.NetworkResult
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@AndroidEntryPoint
class HomeActivity : AppCompatActivity(){

     lateinit var binding: ActivityHomeBinding
    private val authViewModel: AuthViewModel by viewModels()

    private lateinit var navHostFragment:NavHostFragment
   private lateinit var navController:NavController
    private lateinit var appBarConfiguration:AppBarConfiguration

//    private val obBackPressedCallback=object :OnBackPressedCallback(true){
//        override fun handleOnBackPressed() {
//            onBackpressedMethod()
//        }
//    }

//    private fun onBackpressedMethod() {
//       if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)){
//          binding.drawerLayout.closeDrawer(GravityCompat.START)
//       }else{
//finish()
//       }
//    }

    @Inject
    lateinit var dataStorePrefrenceImpl: DataStorePrefrenceImpl
    private val toggle by lazy {
        ActionBarDrawerToggle(
            this,
            binding.drawerLayout,
            binding.toolbar,
            R.string.open,
            R.string.close
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        runBlocking { getdata() }



        //connection button navigation
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bottomNavigationView.setupWithNavController(navController)

//        appBarConfiguration= AppBarConfiguration(navController.graph,binding.drawerLayout)
//        setupActionBarWithNavController(navController,appBarConfiguration)


        //connection drawwer
        binding.txtAppName.text = Constants.provideProjectName(this)
        binding.drawerLayout.addDrawerListener(toggle)

       // setSupportActionBar(binding.toolbar)

        val header=binding.navigationView.getHeaderView(0)
        val userNameText=header.findViewById<TextView>(R.id.txt_username)
        val gmailText=header.findViewById<TextView>(R.id.txt_Email)
        val imageUser=header.findViewById<ImageView>(R.id.img_user)
        userNameText.text=Constants.Name
        gmailText.text=Constants.Email
        if (Constants.imageUrl!=null){
        Glide.with(this).load(Constants.imageUrl).into(imageUser)}





        //action at any fragment and change
        navController.addOnDestinationChangedListener { _, destination, _ ->
            hidenFragment(destination.id)
        }




    }




    private fun hidenFragment(destinationId: Int) {
        when (destinationId) {
            R.id.searchFragment -> {
                binding.bottomNavigationView.isVisible = false
            }

            R.id.weatherFragment -> {
                binding.bottomNavigationView.isVisible = false
            }

            R.id.checkingFragment -> {
                binding.bottomNavigationView.isVisible = false
            }

            R.id.changeProfileFragment -> {
                binding.bottomNavigationView.isVisible = false
            }
            R.id.askingFragment -> {
                binding.bottomNavigationView.isVisible = false
            }
            R.id.postFragment -> {
                binding.bottomNavigationView.isVisible = false
            }
            R.id.diagnosticResultsFragment -> {
                binding.bottomNavigationView.isVisible = false
            }

            else -> {
                binding.bottomNavigationView.isVisible = true
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController=findNavController(R.id.fragment)

        return navController.navigateUp(appBarConfiguration)||super.onSupportNavigateUp()
    }

    private fun getdata() {
        lifecycleScope.launch(Dispatchers.IO) {
            launch {
                dataStorePrefrenceImpl.getPreference(DataStorePrefrenceImpl.userId_KEY, "")
                    .collect {
                        Constants.Id = it
                    }
            }
            launch {
                dataStorePrefrenceImpl.getPreference(DataStorePrefrenceImpl.Name_KEY, "").collect {
                    Constants.Name = it
                }
            }
            launch {
                dataStorePrefrenceImpl.getPreference(DataStorePrefrenceImpl.Email_KEY, "").collect {
                    Constants.Email = it

                }
            }
            launch {
                dataStorePrefrenceImpl.getPreference(DataStorePrefrenceImpl.Token_KEY, "").collect {
                    Constants.Token = it

                }
            }
            launch {
                dataStorePrefrenceImpl.getPreference(DataStorePrefrenceImpl.Country_KEY, "")
                    .collect {
                        Constants.Country = it

                    }
            }
            launch {
                dataStorePrefrenceImpl.getPreference(DataStorePrefrenceImpl.Bio_KEY, "").collect {
                    Constants.Bio = it
                }
            }
            launch {
                dataStorePrefrenceImpl.getPreference(DataStorePrefrenceImpl.ImageUrl_KEY, "")
                    .collect {
                        Constants.imageUrl = it.toUri()

                    }
            }

        }


    }

    private fun logoutAndObserve() {
        authViewModel.logout(Constants.Id)
        authViewModel.response.observe(this) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    lifecycleScope.launch {
                        dataStorePrefrenceImpl.clearAllPreference()
                        dataStorePrefrenceImpl.putPreference(
                            DataStorePrefrenceImpl.onboardingOpenedState_Key,
                            true
                        )
                    }
                    startActivity(Intent(this, AuthenticationActivity::class.java))
                    finish()

                }

                is NetworkResult.Error -> {
                    Log.e("error msg logout", response.errMsg + response.code)
                    Toast.makeText(this, "Failed Logout", Toast.LENGTH_SHORT).show()
                }

                is NetworkResult.Exception -> {}
            }
        }
    }




}


