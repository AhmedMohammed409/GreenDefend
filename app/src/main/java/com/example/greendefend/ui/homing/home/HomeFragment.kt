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
import com.example.greendefend.data.repository.DataStorePrefrenceImpl
import com.example.greendefend.databinding.FragmentHomeBinding
import com.example.greendefend.domin.model.weather.CurrentWeather
import com.example.greendefend.domin.useCase.viewModels.WeatherViewModel
import com.example.greendefend.utli.NetworkResult
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class HomeFragment : Fragment() {
    private lateinit var mFusedLocationProviderClient: FusedLocationProviderClient


    @Inject
    lateinit var dataStorePrefrenceImpl: DataStorePrefrenceImpl
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
            if (isLocationEnable()){
            getCurrentLocation()}
            else{
                startActivity(Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS))
            }
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
            mFusedLocationProviderClient =
                LocationServices.getFusedLocationProviderClient(requireActivity())
           mFusedLocationProviderClient.lastLocation.addOnCompleteListener { task->
               val location=task.result
               if (location != null) {
                   latitude = location.latitude.toFloat()
                   longitude = location.longitude.toFloat()
                   weatherAndObserve(latitude!!, longitude!!)
               }
               else{
                   Toast.makeText(requireContext(),"Null Recieved",Toast.LENGTH_SHORT).show()
               }
            }


    }


    private fun weatherAndObserve(latitude: Float, longitude: Float) {
        weatherviewModel.getCurrentWeather(latitude, longitude)
        binding.progressBar.visibility = View.VISIBLE
        weatherviewModel.response.observe(viewLifecycleOwner) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    binding.progressBar.visibility = View.GONE
                    val responseWeather = response.data as CurrentWeather
                    binding.currentWeather = responseWeather
//                    Glide.with(requireContext())
//                        .load("http://api.weatherapi.com${responseWeather.current?.condition?.icon}")
//                        .into(binding.imgWeather)
                }

                is NetworkResult.Error -> {
//                    binding.progressBar.visibility = View.GONE
                    Log.e("MsgErr", response.toString())
                    Toast.makeText(
                        requireContext(),
                        response.errMsg + "Not found weather",
                        Toast.LENGTH_LONG
                    ).show()
                }

                is NetworkResult.Exception -> {
//                    binding.progressBar.visibility = View.GONE
                    Log.e("MsgErr Exeption", response.e.toString())

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





