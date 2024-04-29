package com.example.greendefend.ui.homing.weather

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.example.greendefend.databinding.FragmentWeatherBinding
import dagger.hilt.android.AndroidEntryPoint



@AndroidEntryPoint
class WeatherFragment : Fragment() {
private lateinit var binding:FragmentWeatherBinding

    private val viewModel: ViewModelWeather by lazy{
        ViewModelProvider(requireActivity())[ViewModelWeather::class.java]
    }
//    private val adapterRV: AdapterRV by lazy{
//       AdapterRV()
//    }
    private val args:WeatherFragmentArgs by navArgs()

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
        binding=FragmentWeatherBinding.inflate(inflater,container,false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

Toast.makeText(requireContext(),"${args.latitude},${args.longitude}",Toast.LENGTH_LONG).show()
//        binding.recyclerView.adapter=adapterRV


//        observeWeather()

    }

//    private fun observeWeather(){
//        viewModel.getWeather(args.latitude,args.longitude)
//        viewModel.weatherLiveData.observe(requireActivity()){
//
//            if (it.isSuccessful){
////               adapterRV.submitList(it.body())
//                Toast.makeText(requireContext(),"Succesfull",Toast.LENGTH_LONG).show()
//
//            }
//            else {
//                Toast.makeText(requireContext(), "Found error to get weather", Toast.LENGTH_LONG).show()
//            }
//        }
//    }


}