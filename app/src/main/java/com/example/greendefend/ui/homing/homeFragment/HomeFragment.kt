package com.example.greendefend.ui.homing.homeFragment

import android.Manifest.permission
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.greendefend.R
import com.example.greendefend.databinding.FragmentHomeBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragment : Fragment() {
    private  var latitude: Float?= 0F
    private var longitude: Float ?= 0F
    private lateinit var binding: FragmentHomeBinding
    private var permissions = arrayOf(permission.ACCESS_FINE_LOCATION,permission.ACCESS_COARSE_LOCATION)
    private val permissionLauncher by lazy {
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            val granted = permissions.entries.all { it.value }
            if (granted) {
               getCurrentLocation()
            } else {
                Toast.makeText(requireContext(), "No Permission Granted", Toast.LENGTH_SHORT).show()
                binding.progressBar.visibility=View.GONE
            }
        }
    }

    private fun hasPermission(permissions: Array<String>): Boolean =
        permissions.all {
            ActivityCompat.checkSelfPermission(requireContext(), it) == PackageManager.PERMISSION_GRANTED
        }

    private fun chekPermissionOrShowDialog() {
        if (hasPermission(permissions)){
            getCurrentLocation()
        }
        else{ permissionLauncher.launch(permissions)
        }

    }

    private val mFusedLocationProviderClient: FusedLocationProviderClient by lazy {
        LocationServices.getFusedLocationProviderClient(requireContext())
    }
    private val viewModel: ViewModelCurrentWeather by viewModels()

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
        chekPermissionOrShowDialog()
       weatherAndObserve(latitude!!, longitude!!)

        val actiotogle = ActionBarDrawerToggle(
            requireActivity(), binding.drawer, binding.toolbar, R.string.open, R.string.close)
        binding.drawer.addDrawerListener(actiotogle)

        binding.btnUpload.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToCheckingFragment())
        }
        binding.txtMoreWeather.setOnClickListener {
            findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToWeatherFragment(latitude!!.toFloat(), longitude!!.toFloat()))
        }

        binding.imgMoreExpand.setOnClickListener {
            val visability = if (binding.noteWeather.visibility == View.GONE) {
                View.VISIBLE
            } else {
                View.GONE
            }
            binding.noteWeather.visibility=visability
            binding.txtMoreWeather.visibility=visability
        }


    }


    @SuppressLint("MissingPermission")
    private fun getCurrentLocation() {
        val task = mFusedLocationProviderClient.lastLocation
        task.addOnSuccessListener {
            latitude=it.latitude.toFloat()
            longitude=it.longitude.toFloat()
        }
    }


    private fun observeWeather() {
        viewModel.getCurrentWeather(latitude!!.toFloat(),longitude!!.toFloat())
        viewModel.resultLiveData.observe(viewLifecycleOwner) {
                binding.currentWeather = it
                binding.progressBar.visibility = View.GONE
//                Glide.with(requireContext())
//                    .load("http://api.weatherapi.com${it.current?.condition?.icon}")
//                    .into(binding.imgWeather)

        }

    }
    private fun weatherAndObserve(latitude: Float, longitude:Float) {
        viewModel.getCurrentWeather(latitude, longitude)
        viewModel.connectionError.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                binding.progressBar.visibility = View.GONE
                viewModel.rest()
            }
        }
        viewModel.serverResponse.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                binding.progressBar.visibility = View.GONE
                viewModel.rest()
            }
        }

        viewModel.resultLiveData.observe(viewLifecycleOwner){
            if (it!=null) {
                binding.progressBar.visibility = View.GONE
                binding.currentWeather=it
            }
        }
    }


    }




