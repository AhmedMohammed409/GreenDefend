package com.example.greendefend.ui.homing.weather

import android.annotation.SuppressLint
import android.content.Context
import android.icu.text.DecimalFormat
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.greendefend.R
import com.example.greendefend.databinding.RowWeatherBinding
import com.example.greendefend.domin.model.weather.List


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
            val dec = DecimalFormat("#.##")
            val temp = dec.format((item.main!!.temp!! -272.25))
            binding.txtTemperature.text= temp.toString()+ context.getString(R.string.c)

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

