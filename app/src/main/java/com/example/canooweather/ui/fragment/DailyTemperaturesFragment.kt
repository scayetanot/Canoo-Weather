package com.example.canooweather.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.canooweather.MainApplication
import com.example.canooweather.R
import com.example.canooweather.data.entity.DailyDataEntity
import com.example.canooweather.databinding.DetailsFragmentBinding
import com.example.canooweather.ui.MainActivity.Companion.UUID_KEY
import com.example.canooweather.utils.viewModelProvider
import javax.inject.Inject

class DailyTemperaturesFragment: Fragment() {

    private lateinit var temperaturesAdapter: TemperaturesAdapter
    private val appComponents by lazy { MainApplication.appComponents }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private fun getViewModel(): DailyTemperatureViewModel {
        return viewModelProvider(viewModelFactory)
    }

    private lateinit var binding: DetailsFragmentBinding
    private var uuid: String? = "NO_UUID"

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        appComponents.inject(this)
        binding = DataBindingUtil.inflate(inflater, R.layout.details_fragment, container,false)
        uuid = arguments?.getString(UUID_KEY)
        binding.close.setOnClickListener(){
            activity?.onBackPressed();
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initObservers()
    }

    private fun initViews() {
        getViewModel().getHourlyTemperatures(uuid)
    }

    private fun initObservers() {
        getViewModel().resultDailyTemperature.observe(viewLifecycleOwner, Observer { temperaturesList ->
            temperaturesList?.let {
                initRecycler(it)
            }
        }
        )

        getViewModel().errorMessage.observe(viewLifecycleOwner, Observer {
            Toast.makeText(this.context,"Connection Error", Toast.LENGTH_LONG).show();
        })
    }

    private fun initRecycler(list: List<DailyDataEntity>) {
        if (!list.isNullOrEmpty()) {
            temperaturesAdapter = TemperaturesAdapter(list)//, this)
            binding.detailsRecycler.apply {
                layoutManager = LinearLayoutManager(activity)
                adapter = temperaturesAdapter
            }
        }
    }
}