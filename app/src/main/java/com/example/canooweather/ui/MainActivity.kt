package com.example.canooweather.ui

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.canooweather.MainApplication
import com.example.canooweather.R
import com.example.canooweather.databinding.ActivityMainBinding
import com.example.canooweather.utils.*
import com.facebook.drawee.backends.pipeline.Fresco
import javax.inject.Inject


class MainActivity : AppCompatActivity() {
    private val appComponents by lazy { MainApplication.appComponents }

    private val LOCATION_REQUEST_CODE = 666
    private val DFLT_LAT: Double = 33.942791
    private val DFLT_LONG: Double = -118.410042


    private var latitude: Double = DFLT_LAT
    private var longitude: Double = DFLT_LONG

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    lateinit var binding: ActivityMainBinding
     var city: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponents.inject(this)
        Fresco.initialize(this)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        //setContentView(R.layout.activity_main)

        //fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        initViews()
        initObservers()
        checkLocationPermission()

        binding.weatherLayout.setOnClickListener {
            if (savedInstanceState == null) {
                val intent = Intent(this, DetailsActivity::class.java)
                intent.putExtra(EXTRA_CITY, city)
                startActivity(intent)
            }
        }

    }

    private fun getViewModel(): MainActivityViewModel {
        return viewModelProvider(viewModelFactory)
    }

    private fun initViews() {
        getViewModel().getForeCast(this, latitude, longitude )
    }

    private fun initObservers() {

        getViewModel().forecastResponse.observe(this, Observer {
            binding.locationWeatherPic.setImageURI(convertToUri(it.current.weather.first().icon))
            binding.locationCurrentTemperature.text = formatTemperature(it.current.temp)
            city = it.city
            binding.locationName.text = it.city
            binding.locationSummary.text = it.current.weather.first().main
            binding.highTemp.text = formatTemperature(it.daily.first().temp.max)
            binding.lowTemp.text = formatTemperature(it.daily.first().temp.min)
            binding.sunrise.text = convertToReadableDate(it.current.sunrise)
            binding.sunset.text = convertToReadableDate(it.current.sunset)
            binding.humidity.text = """${it.current.humidity}${getString(R.string.percentage)}"""
        })

     //   getViewModel().findCityResponse.observe(this, Observer {
     //       binding.locationName.text = it
     //   })

        getViewModel().errorMessage.observe(this, Observer {
            Toast.makeText(this,"Connection Error",Toast.LENGTH_LONG).show();
        })
    }

    private fun checkLocationPermission() {
        if(ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if(ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                showWhyLocationIsNeededAndRequestPermission()
            } else {
                requestPermission()
            }
        } else {
            requestLocation()
        }
    }

    private fun showWhyLocationIsNeededAndRequestPermission() {
        val dialogBox = AlertDialog.Builder(this)
        dialogBox.setMessage(getString(R.string.location_req_desc))
            .setTitle(R.string.location_req_title)
        dialogBox.setPositiveButton(R.string.ok) { _, _ ->
            requestPermission()
        }
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(this,
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            LOCATION_REQUEST_CODE)
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            LOCATION_REQUEST_CODE -> {
                if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Log.d("LOG", "User didn't allowed the location")
                } else {
                    requestLocation();

                }
            }
        }
    }

    private fun requestLocation(){
        getRealLocation()
    }

    private fun getRealLocation(){
        val locationManager = getSystemService(LOCATION_SERVICE) as LocationManager?
        try {
            locationManager?.requestLocationUpdates(LocationManager.NETWORK_PROVIDER
                    , 0L, 0f, locationListener)
            locationManager?.requestLocationUpdates(LocationManager.GPS_PROVIDER
                    , 0L, 0f, locationListener)
        } catch(ex: SecurityException) {
            Log.d("myTag", "Security Exception, no location available")
        }
    }

    private val locationListener: LocationListener = object : LocationListener {
        override fun onLocationChanged(location: Location) {
            latitude = location.latitude
            longitude = location.longitude
          //  getViewModel().getForeCast(applicationContext, latitude, longitude)
        }
        override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
        override fun onProviderEnabled(provider: String) {}
        override fun onProviderDisabled(provider: String) {}
    }

    companion object {
        const val EXTRA_CITY = "city"
    }
}