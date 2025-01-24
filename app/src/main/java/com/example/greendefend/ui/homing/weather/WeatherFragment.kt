package com.example.greendefend.ui.homing.weather

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.greendefend.R
import com.example.greendefend.data.local.Converters
import com.example.greendefend.databinding.FragmentWeatherBinding
import com.example.greendefend.domin.model.weather.AllWeather
import com.example.greendefend.domin.useCase.viewModels.WeatherViewModel
import com.example.greendefend.ui.homing.HomeActivity
import com.example.greendefend.utli.NetworkResult
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.roundToInt


@AndroidEntryPoint
class WeatherFragment : Fragment() {
private lateinit var binding:FragmentWeatherBinding
private val weatherViewModel:WeatherViewModel by viewModels ()

    private val adapterRV: AdapterRV by lazy{
       AdapterRV(requireContext())
    }
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

        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.recyclerView.adapter=adapterRV
        weatherAndObserve(args.latitude,args.longitude)

    }

    @SuppressLint("SetTextI18n", "DefaultLocale")
    private fun weatherAndObserve(latitude: Float, longitude: Float) {
        weatherViewModel.getWeather(latitude, longitude)
        weatherViewModel.response.observe(viewLifecycleOwner){response->
            when(response){
            is NetworkResult.Success-> {
            binding.progressBar.visibility = View.GONE
                val result=response.data as AllWeather
            adapterRV.submitList( result.list)
            binding.noteWeather.text= result.list[0].weather[0].description
            binding.windSpeed.text= getString(R.string.wind_speed)+"\t"+result.list[0].wind!!.speed
            binding.txtLocation.text=  result.city!!.name
                binding.txtDay.text= Converters().convertDateTimeToDayName(result.list[0].dtTxt!!)
           // val dec = DecimalFormat("#")

            var temp = result.list[0].main!!.temp!! -272.25F
                val formattedNum = String.format("%.2f", temp)
                Log.e("apro num",formattedNum)
                temp= temp.roundToInt().toFloat()



            binding.txtTemperature.text= "$temp C"

            Glide.with(requireContext())
                .load("https://openweathermap.org/img/wn/${result.list[0].weather[0].icon}@2x.png")
                .into(binding.imgWeather)
        }
            is NetworkResult.Error->{
                binding.progressBar.visibility = View.GONE
                Toast.makeText(requireContext(),"failed to get Weather",Toast.LENGTH_SHORT).show()
            }

            }

        }

    }
    override fun onAttach(context: Context) {
        (requireActivity() as HomeActivity).binding.toolbar.visibility=View.GONE
        super.onAttach(context)
    }

    override fun onDestroy() {
        (requireActivity() as HomeActivity).binding.toolbar.visibility=View.VISIBLE
        super.onDestroy()
    }
}