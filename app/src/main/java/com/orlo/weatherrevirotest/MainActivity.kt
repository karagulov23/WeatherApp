package com.orlo.weatherrevirotest

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Looper
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.Priority
import com.orlo.weatherrevirotest.presentation.ui.screens.MyLocation
import com.orlo.weatherrevirotest.presentation.ui.screens.weather.WeatherScreen
import com.orlo.weatherrevirotest.presentation.ui.theme.WeatherReviroTestTheme
import com.orlo.weatherrevirotest.util.Event
import com.orlo.weatherrevirotest.util.EventBus
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var fusedLocationProvideClient: FusedLocationProviderClient
    private lateinit var locationCallback: LocationCallback
    private var locationRequired: Boolean = false

    override fun onResume() {
        super.onResume()
        if (locationRequired) startLocationUpdate()
    }

    override fun onPause() {
        super.onPause()
        locationCallback.let {
            fusedLocationProvideClient.removeLocationUpdates(it)
        }
    }

    @SuppressLint("MissingPermission")
    private fun startLocationUpdate() {
        locationCallback.let {
            val locationRequest = com.google.android.gms.location.LocationRequest.Builder(
                Priority.PRIORITY_HIGH_ACCURACY, 100
            )
                .setWaitForAccurateLocation(false)
                .setMinUpdateIntervalMillis(3000)
                .setMaxUpdateAgeMillis(100)
                .build()

            fusedLocationProvideClient.requestLocationUpdates(
                locationRequest,
                it,
                Looper.getMainLooper()
            )
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        initLocationClient()
        setContent {
            //This will keep value of our current location
            var currentLocation by remember {
                mutableStateOf(MyLocation(0.0, 0.0))
            }
            //Implement location callback
            locationCallback = object : LocationCallback() {
                override fun onLocationResult(p0: LocationResult) {
                    super.onLocationResult(p0)
                    for (location in p0.locations) {
                        currentLocation = MyLocation(
                            location.latitude,
                            location.longitude
                        )
                    }
                }
            }

            WeatherReviroTestTheme {
                LaunchedEffect(key1 = lifecycle) {
                    repeatOnLifecycle(state = Lifecycle.State.STARTED) {
                        EventBus.events.collect { event ->
                            if (event is Event.Toast) {
                                Toast.makeText(this@MainActivity, event.message, Toast.LENGTH_SHORT)
                                    .show()
                            }
                        }
                    }
                }

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    WeatherScreen()
//                    LocationScreen(this@MainActivity, currentLocation)

//                            WeatherScreen()
//                    Column(
//                        modifier = Modifier
//                            .fillMaxSize()
//                            .background(Color.Black)
//                    ) {
//                        Text(text = "CUURUEOIRU")
//                        Text(text = currentLocation.lat.toString())
//                        Text(text = currentLocation.lon.toString())
//                    }
                }
            }
        }
    }
}

//    @Composable
//    fun LocationScreen(context: Context, currentLocation: MyLocation) {
//
//        val launcherMultiplePermission = rememberLauncherForActivityResult(
//            ActivityResultContracts.RequestMultiplePermissions()
//        ) { permissionMap ->
//            val areGranted = permissionMap.values.reduce { accepted, next ->
//                accepted && next
//            }
//            if (areGranted) {
//                locationRequired = true
//                startLocationUpdate()
//                Toast.makeText(context, "Permission Granted", Toast.LENGTH_SHORT).show()
//            } else {
//                Toast.makeText(context, "Permission Denied", Toast.LENGTH_SHORT).show()
//            }
//        }
//
//        LaunchedEffect(key1 = currentLocation, block = {
//            coroutineScope {
//                if (permissions.all {
//                        ContextCompat.checkSelfPermission(
//                            context,
//                            it
//                        ) == PackageManager.PERMISSION_GRANTED
//                    }) {
//                    startLocationUpdate()
//                } else {
//                    launcherMultiplePermission.launch(permissions)
//                }
//            }
//        })
//
//        Column(modifier = Modifier.fillMaxSize()) {
//            Text(text = currentLocation.lat.toString())
//            Text(text = currentLocation.lon.toString())
//        }
//    }
//
//    private fun initLocationClient() {
//        fusedLocationProvideClient = LocationServices
//            .getFusedLocationProviderClient(this)
//    }


