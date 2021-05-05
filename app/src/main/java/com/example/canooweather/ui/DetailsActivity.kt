package com.example.canooweather.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.canooweather.MainApplication.Companion.appComponents
import com.example.canooweather.R
import com.example.canooweather.data.entity.DailyEntity
import com.example.canooweather.databinding.ActivityDetailsBinding
import com.example.canooweather.utils.viewModelProvider
import javax.inject.Inject

class DetailsActivity : AppCompatActivity() {

    private lateinit var temperaturesAdapter: TemperaturesAdapter
    private lateinit var binding: ActivityDetailsBinding

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private fun getViewModel(): DetailsActivityViewModel {
        return viewModelProvider(viewModelFactory)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        appComponents.inject(this)
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_details);

        actionBar?.setDisplayHomeAsUpEnabled(true);

        initViews()
        initObservers()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        return true
    }

    private fun initViews() {
        getViewModel().getDailyTemperatures()
    }

    private fun initObservers() {
        getViewModel().resultDailyTemperature.observe(this, Observer { temperaturesList ->
            temperaturesList?.let {
                initRecycler(it)
            }
        })

        getViewModel().errorMessage.observe(this, Observer {
            Toast.makeText(this,"Connection Error", Toast.LENGTH_LONG).show();
            finish()
        })
    }

    private fun initRecycler(list: List<DailyEntity>) {
        if (!list.isNullOrEmpty()) {
            temperaturesAdapter =
                TemperaturesAdapter(list)
            binding.detailsRecycler.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = temperaturesAdapter
            }
        }
    }


}
