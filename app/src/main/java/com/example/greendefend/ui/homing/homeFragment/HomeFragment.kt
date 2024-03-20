package com.example.greendefend.ui.homing.homeFragment

import android.Manifest.permission
import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.greendefend.R
import com.example.greendefend.databinding.FragmentHomeBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener


class HomeFragment : Fragment() {
    private  var latitude: Float?= 0F
    private var longitude: Float ?= 0F
    private lateinit var binding: FragmentHomeBinding
    private val mFusedLocationProviderClient: FusedLocationProviderClient by lazy {
        LocationServices.getFusedLocationProviderClient(requireContext())
    }

    private val viewModel: ViewModelCurrentWeather by lazy{
        ViewModelProvider(requireActivity())[ViewModelCurrentWeather::class.java]
    }

    override fun onAttach(context: Context) {
        getCurrentLocation()
        super.onAttach(context)
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

        observeWeather(latitude!!.toFloat(), longitude!!.toFloat())

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
            binding.noteSun.visibility=visability
        }


    }


    @SuppressLint("MissingPermission")
    private fun getCurrentLocation() {
        val task = mFusedLocationProviderClient.lastLocation
        if (cheekPermission()) {
            task.addOnSuccessListener {
               latitude=it.latitude.toFloat()
                longitude=it.longitude.toFloat()
            }

        } else {
            Toast.makeText(requireContext(), "Failed Permision", Toast.LENGTH_SHORT).show()
        }
    }

    private fun cheekPermission(): Boolean {
        var isGranted = true
        Dexter.withContext(requireContext()).withPermissions(
            permission.ACCESS_COARSE_LOCATION,
            permission.ACCESS_FINE_LOCATION,
            permission.INTERNET
        ).withListener(object : MultiplePermissionsListener {
            override fun onPermissionsChecked(p0: MultiplePermissionsReport?) =
                if (!p0!!.areAllPermissionsGranted()) {
                    Toast.makeText(
                        requireContext(),
                        "Please allows permison",
                        Toast.LENGTH_SHORT
                    ).show()
                    isGranted = false
                } else {
                }

            override fun onPermissionRationaleShouldBeShown(
                p0: MutableList<PermissionRequest>?,
                p1: PermissionToken?
            ) {
                p1!!.continuePermissionRequest()
            }
        }).check()

        return isGranted
    }

    private fun observeWeather(latitude:Float,longitude:Float){
        viewModel.getCurrentWeather(latitude,longitude)
        viewModel.currentWeatherMutableLiveData.observe(viewLifecycleOwner) {

            if (it.isSuccessful) {
                binding.currentWeather = it.body()
                binding.progressBar.visibility = View.GONE
                Glide.with(requireContext()).load("http://api.weatherapi.com${it.body()!!.current!!.condition!!.icon}").into(binding.imgWeather)
            }
        }
    }




}