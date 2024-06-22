package com.example.greendefend.ui.homing.weather

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.greendefend.data.local.Converters
import com.example.greendefend.databinding.RowWeatherBinding
import com.example.greendefend.domin.model.weather.List
import kotlin.math.roundToInt


class AdapterRV(var context: Context) : ListAdapter<List, AdapterRV.ViewHolder>(WeatherDiffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(RowWeatherBinding.inflate(LayoutInflater.from(parent.context))
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    inner class ViewHolder(private val binding: RowWeatherBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(item: List) {
            binding.weather = item
            var temp=(item.main!!.temp!!).toFloat()-272.25F
            temp= temp.roundToInt().toFloat()
            binding.txtTemperature.text= "$temp C"
            binding.txtDay.text= Converters().convertDateTimeToDayName(item.dtTxt!!)

            Glide.with(context)
                .load("https://openweathermap.org/img/wn/${item.weather[0].icon}@2x.png")
                .into(binding.imgWeather)
        }
    }
}
object WeatherDiffUtil : DiffUtil.ItemCallback<List>() {
    override fun areItemsTheSame(oldItem: List, newItem: List): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: List, newItem: List): Boolean {
        return oldItem.dt == newItem.dt
    }
}

