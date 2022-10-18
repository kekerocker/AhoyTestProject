package com.dsoft.presentation.ui.main.fragment.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dsoft.domain.extension.shrinkToTwoDigits
import com.dsoft.domain.model.TemperatureType
import com.dsoft.domain.model.WeatherForecast
import com.dsoft.presentation.databinding.ItemWeatherBinding

class WeatherForecastAdapter :
    RecyclerView.Adapter<WeatherForecastAdapter.WeatherForecastViewHolder>() {

    var listWeather: List<WeatherForecast> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var temperatureType: TemperatureType = TemperatureType.CELSIUS
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    inner class WeatherForecastViewHolder(binding: ItemWeatherBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var tvTemperature = binding.tvTemperature
        var tvDate = binding.tvDate
        var tvHumidity = binding.tvHumidity
        var tvPressure = binding.tvPressure
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherForecastViewHolder {
        return WeatherForecastViewHolder(
            ItemWeatherBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: WeatherForecastViewHolder, position: Int) {
        val currentItem = listWeather[position]
        with(holder) {
            tvDate.text = currentItem.date
            tvHumidity.text = currentItem.humidityString
            tvPressure.text = currentItem.pressureString
            convertTemperature(holder, temperatureType, currentItem)
        }
    }

    override fun getItemCount(): Int = listWeather.size

    private fun convertTemperature(
        holder: WeatherForecastViewHolder,
        tempType: TemperatureType,
        currentItem: WeatherForecast
    ) {
        when (tempType) {
            TemperatureType.CELSIUS -> holder.tvTemperature.text =
                "${currentItem.temperatureCelsius.shrinkToTwoDigits()}°C"
            TemperatureType.FAHRENHEIT -> holder.tvTemperature.text =
                "${currentItem.temperatureFahrenheit}℉"
        }
    }
}