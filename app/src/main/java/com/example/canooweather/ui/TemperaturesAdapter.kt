package com.example.canooweather.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.databinding.DataBindingUtil
import com.example.canooweather.R
import com.example.canooweather.data.entity.DailyEntity
import com.example.canooweather.databinding.DailyTempItemLayoutBinding
import com.example.canooweather.utils.convertToReadableDay
import com.example.canooweather.utils.convertToUri
import com.example.canooweather.utils.findDrawable
import com.example.canooweather.utils.formatTemperature

class TemperaturesAdapter(
        var listOfTemperatures: List<DailyEntity>) : RecyclerView.Adapter<TemperaturesAdapter.ViewHolder>() {

    override fun getItemCount(): Int {
        return listOfTemperatures.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            parent.context,
                DataBindingUtil.inflate(
                        LayoutInflater.from(parent.context),
                        R.layout.daily_temp_item_layout,
                        parent,
                        false
                )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindViewHolder(listOfTemperatures[position])
    }

    inner class ViewHolder(private val context: Context, private val viewDataBinding: DailyTempItemLayoutBinding) :
            RecyclerView.ViewHolder(viewDataBinding.root) {

        fun bindViewHolder(dailyTemperature: DailyEntity) {
            viewDataBinding.day.text = convertToReadableDay(dailyTemperature.dt)
            viewDataBinding.temp.text = formatTemperature(dailyTemperature.temp.day)
            viewDataBinding.tempMax.text = formatTemperature(dailyTemperature.temp.max)
            viewDataBinding.tempMin.text = formatTemperature(dailyTemperature.temp.min)
            viewDataBinding.icon.setImageURI(convertToUri(dailyTemperature.weather.first().icon))
        }
    }

}