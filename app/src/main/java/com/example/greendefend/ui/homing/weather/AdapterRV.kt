//package com.example.greendefend.ui.homing.weatherFragment
//
//import android.view.LayoutInflater
//import android.view.ViewGroup
//import androidx.recyclerview.widget.DiffUtil
//import androidx.recyclerview.widget.ListAdapter
//import androidx.recyclerview.widget.RecyclerView
//import com.example.greendefend.databinding.RowWeatherBinding
//import com.example.greendefend.model.weather.Weather
//
//
//class AdapterRV : ListAdapter<Weather, AdapterRV.ViewHolder>(WeatherDiffUtil) {
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        return ViewHolder(RowWeatherBinding.inflate(LayoutInflater.from(parent.context))
//        )
//    }
//
//    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        holder.bind(currentList[position])
//    }
//
//    inner class ViewHolder(private val binding: RowWeatherBinding) :
//        RecyclerView.ViewHolder(binding.root) {
//
//        fun bind(item: Weather) {
//            binding.weather = item
//        }
//    }
//}
//object WeatherDiffUtil : DiffUtil.ItemCallback<Weather>() {
//    override fun areItemsTheSame(oldItem: Weather, newItem: Weather): Boolean {
//        return oldItem == newItem
//    }
//
//    override fun areContentsTheSame(oldItem: Weather, newItem: Weather): Boolean {
//        return oldItem.code == newItem.code
//    }
//}
//
