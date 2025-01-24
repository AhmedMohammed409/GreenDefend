package com.example.greendefend.ui.homing.home

import android.Manifest.permission
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.greendefend.R
import com.example.greendefend.data.local.Converters
import com.example.greendefend.databinding.FragmentHomeBinding
import com.example.greendefend.domin.model.weather.AllWeather
import com.example.greendefend.domin.useCase.viewModels.WeatherViewModel
import com.example.greendefend.ui.homing.HomeActivity
import com.example.greendefend.utli.NetworkResult
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.roundToInt


@AndroidEntryPoint
class HomeFragment : Fragment() {
    private lateinit var mFusedLocationProviderClient: FusedLocationProviderClient
    private val tag="HomeFragment"

    private val weatherviewModel: WeatherViewModel by viewModels()
    private var latitude: Float? = 30.033333F
    private var longitude: Float? = 31.233334F
    private lateinit var binding: FragmentHomeBinding
    private var permissions =
        arrayOf(permission.ACCESS_FINE_LOCATION, permission.ACCESS_COARSE_LOCATION)
    private val permissionLauncher by lazy {
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            val granted = permissions.entries.all { it.value }
            if (granted) {
                getCurrentLocation()
            } else {
                Toast.makeText(requireContext(), "No Permission Granted", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun hasPermission(permissions: Array<String>): Boolean =
        permissions.all {
            ActivityCompat.checkSelfPermission(
                requireContext(),
                it
            ) == PackageManager.PERMISSION_GRANTED
        }

    private fun checkPermissionOrShowDialog() {
        if (hasPermission(permissions)) {
            if (isLocationEnable()) {
                getCurrentLocation()
            }
//            else{
//                startActivity(Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS))
//            }
        } else {
            permissionLauncher.launch(permissions)
        }

    }


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

        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkPermissionOrShowDialog()
        binding.btn1.setOnClickListener {
            findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToDiseasePlantsFragment(
                    1
                )
            )
        }
        binding.btn2.setOnClickListener {
            findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToDiseasePlantsFragment(
                    2
                )
            )
        }
        binding.btn3.setOnClickListener {
            findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToDiseasePlantsFragment(
                    3
                )
            )
        }

        binding.btnUpload.setOnClickListener {
            findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToCheckingFragment(
                    true
                )
            )
        }
        binding.btnCamera.setOnClickListener {
            findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToCheckingFragment(
                    false
                )
            )
        }
        binding.txtMoreWeather.setOnClickListener {
            findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToWeatherFragment(
                    latitude!!.toFloat(),
                    longitude!!.toFloat()
                )
            )
        }

        binding.imgMoreExpand.setOnClickListener {
            val visability = if (binding.noteWeather.visibility == View.GONE) {
                View.VISIBLE
            } else {
                View.GONE
            }
            binding.noteWeather.visibility = visability
            binding.txtMoreWeather.visibility = visability
        }


    }


    @SuppressLint("MissingPermission")
    private fun getCurrentLocation() {
        try {
            mFusedLocationProviderClient =
                LocationServices.getFusedLocationProviderClient(requireActivity())

            mFusedLocationProviderClient.lastLocation.addOnCompleteListener { task ->
                val location = task.result
                if (location != null) {
                    latitude = location.latitude.toFloat()
                    longitude = location.longitude.toFloat()
                    Log.i("Latit : "+tag, latitude.toString())
                    Log.i("Long  : "+tag, longitude.toString())
                    weatherAndObserve(latitude!!, longitude!!)
                } else {
                    Toast.makeText(requireContext(), "Please open GPS", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS))
                }
            }
        } catch (e: Exception) {
            Toast.makeText(requireContext(), "Please connect by internt", Toast.LENGTH_SHORT).show()
        }


    }

    @SuppressLint("SetTextI18n")
    private fun weatherAndObserve(latitude: Float, longitude: Float) {
        weatherviewModel.getWeather(latitude, longitude)
        binding.progressBar.visibility = View.VISIBLE
        weatherviewModel.response.observe(viewLifecycleOwner) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    binding.progressBar.visibility = View.GONE
                    val result = response.data as AllWeather
                    binding.currentWeather = result
                    binding.windSpeed.text =
                        getString(R.string.wind_speed) + "\t" + result.list[0].wind!!.speed
                    var temp = (result.list[0].main!!.temp!!).toFloat() - 272.25F
                    binding.txtDay.text =
                        Converters().convertDateTimeToDayName(result.list[0].dtTxt!!)
                    temp = temp.roundToInt().toFloat()
                    binding.txtTemperature.text = "$temp C"
                    Glide.with(requireContext())
                        .load("https://openweathermap.org/img/wn/${result.list[0].weather[0].icon}@2x.png")
                        .into(binding.imgWeather)
                }

                is NetworkResult.Error -> {
                    binding.progressBar.visibility = View.GONE
                    if (response.code==401){
                        ( requireActivity() as HomeActivity).logoutAndObserve()
                    }else if (response.code==400){
                        Toast.makeText(
                            requireContext(),
                           "Bad Request",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                    Log.i(tag, response.toString())
                    Toast.makeText(
                        requireContext(),
                        response.errMsg,
                        Toast.LENGTH_LONG
                    ).show()
                }

            }
        }

    }

    private fun isLocationEnable(): Boolean {
        val locationManager: LocationManager =
            requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }


}





