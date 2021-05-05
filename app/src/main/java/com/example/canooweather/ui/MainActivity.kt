package com.example.canooweather.ui

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.net.Uri
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
import com.example.canooweather.data.entity.DailyEntity
import com.example.canooweather.databinding.ActivityMainBinding
import com.example.canooweather.utils.*
import com.facebook.drawee.backends.pipeline.Fresco
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.location.LocationServices
import javax.inject.Inject


class MainActivity : AppCompatActivity() {
    private val appComponents by lazy { MainApplication.appComponents }

    private val LOCATION_REQUEST_CODE = 666
    private val DFLT_LAT: Double = 33.942791
    private val DFLT_LONG: Double = -118.410042

    private val isInternetOn = InternetUtil.isInternetOn()


    private var latitude: Double = DFLT_LAT
    private var longitude: Double = DFLT_LONG

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponents.inject(this)
        Fresco.initialize(this)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        initViews()
        initObservers()
        checkLocationPermission()

        binding.detailsBtn.setOnClickListener {
            if (savedInstanceState == null) {
                val intent = Intent(this, DetailsActivity::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(this,"No information available",Toast.LENGTH_LONG).show();
            }
        }

    }

    private fun getViewModel(): MainActivityViewModel {
        return viewModelProvider(viewModelFactory)
    }

    private fun initViews() {
        if(isInternetOn)
      //      getViewModel().getForeCast(this, latitude, longitude )
        else {
            displayErrorMessage()
        }
    }

    private fun displayErrorMessage(msg: String = "Connection Error"){
        Toast.makeText(this, msg,Toast.LENGTH_LONG).show();
    }

    private fun initObservers() {

        getViewModel().forecastResponse.observe(this, Observer {
            binding.locationWeatherPic.setImageURI(convertToUri(it.current.weather.first().icon))
            binding.locationCurrentTemperature.text = formatTemperature(it.current.temp)
            binding.locationName.text = it.city
            binding.locationSummary.text = it.current.weather.first().main
            binding.highTemp.text = getString(R.string.max) + " " + formatTemperature(it.daily.first().temp.max)
            binding.lowTemp.text = getString(R.string.min) + " " + formatTemperature(it.daily.first().temp.min)
            binding.sunrise.text = getString(R.string.sunrise) + " " + convertToReadableHours(it.current.sunrise)
            binding.sunset.text = getString(R.string.sunset)  + " "+ convertToReadableHours(it.current.sunset)
            binding.humidity.text = getString(R.string.humidity)  + " " + """${it.current.humidity}${getString(R.string.percentage)}"""
        })

        getViewModel().errorMessage.observe(this, Observer {
            displayErrorMessage()
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
            //acquireLocation() //Commented the use of fused as on emulator it's not working fine
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
                    requestLocation()
                    //acquireLocation() //Commented the use of fused as on emulator it's not working fine

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

    /* Keep the api code as reference for FusedLocation */
    @SuppressLint("MissingPermission")
    private fun acquireLocation() {
        val googleApiAvailability = GoogleApiAvailability.getInstance()
        val result = googleApiAvailability.isGooglePlayServicesAvailable(this)
        if (googleApiAvailability.isUserResolvableError(result)) {
            GoogleApiAvailability.getInstance().getErrorDialog(this, result, 123)
        } else {
            val client = LocationServices.getFusedLocationProviderClient(this)
            client.lastLocation.addOnSuccessListener { location: Location? ->
                location?.let {
                    latitude = it.latitude
                    longitude = it.longitude
                    getViewModel().getForeCast(this, latitude, longitude)
                } ?: displayErrorMessage("Enable to get Location")
            }
        }
    }

    private val locationListener: LocationListener = object : LocationListener {
        override fun onLocationChanged(location: Location) {
            if(location != null) {
                latitude = location.latitude
                longitude = location.longitude
                getViewModel().getForeCast(applicationContext, latitude, longitude)
            }

        }
        override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
        override fun onProviderEnabled(provider: String) {}
        override fun onProviderDisabled(provider: String) {}
    }

    companion object {
        const val EXTRA_CITY = "city"
    }
}