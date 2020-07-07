package com.thomas.apps.nhatrosvkltn.view.screens.map

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.thomas.apps.nhatrosvkltn.databinding.ActivityMapBinding
import com.thomas.apps.nhatrosvkltn.model.FilterModel
import com.thomas.apps.nhatrosvkltn.utils.Constant.Companion.INTENT_APARTMENT_ID
import com.thomas.apps.nhatrosvkltn.utils.TOAST
import com.thomas.apps.nhatrosvkltn.utils.hideSoftKeyboard
import com.thomas.apps.nhatrosvkltn.utils.launchActivity
import com.thomas.apps.nhatrosvkltn.view.screens.MainViewModel
import com.thomas.apps.nhatrosvkltn.view.screens.apartmentdetails.ApartmentDetailsActivity
import com.thomas.apps.nhatrosvkltn.view.screens.filters.FiltersFragment


class MapActivity : AppCompatActivity(), OnMapReadyCallback, FiltersFragment.OnDismissListener {


    private lateinit var viewModel: MapViewModel
    private lateinit var mainViewModel: MainViewModel
    private lateinit var binding: ActivityMapBinding
    private lateinit var filtersFragment: FiltersFragment

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private var mMap: GoogleMap? = null
    private val defaultLocation = LatLng(-33.8523341, 151.2106085)
    private var locationPermissionGranted = false
    private var lastKnownLocation: Location? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(MapViewModel::class.java)
        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)


        init(savedInstanceState)

    }

    @SuppressLint("ClickableViewAccessibility")
    private fun init(savedInstanceState: Bundle?) {

        // Construct a FusedLocationProviderClient.
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        Dexter.withContext(this)
            .withPermissions(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
            .withListener(object : MultiplePermissionsListener {
                @SuppressLint("MissingPermission")
                override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                    report?.let {
                        if (report.areAllPermissionsGranted()) {
                            //TOAST("OK")
                            binding.mapView.getMapAsync(this@MapActivity)
                            mMap?.isMyLocationEnabled = true
                        }
                    }
                }

                override fun onPermissionRationaleShouldBeShown(
                    permissions: MutableList<PermissionRequest>?,
                    token: PermissionToken?
                ) {
                    // Remember to invoke this method when the custom rationale is closed
                    // or just by default if you don't want to use any custom rationale.
                    token?.continuePermissionRequest()
                }
            })
            .withErrorListener {
                TOAST(it.name)
            }
            .check()

        filtersFragment =
            FiltersFragment(
                this,
                android.R.style.Theme_Material_Light_DialogWhenLarge_NoActionBar
            )
        filtersFragment.onDismissListener = this

        with(binding) {
            mapView.onCreate(savedInstanceState)

            buttonBack.setOnClickListener { onBackPressed() }

            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    TOAST("Searching...")
                    hideSoftKeyboard()
                    // search here

                    //recyclerAdapter.filter.filter(editTextSearch.text.toString())
                    //todo call api search
                    if (query != null)
                        viewModel.search(query)
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    return false
                }

            })

            imageButtonFilter.setOnClickListener { filtersFragment.show() }
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        //mMap!!.setInfoWindowAdapter(CustomInfoWindowAdapter(this))

        mMap!!.setOnMarkerClickListener { marker ->

            mMap!!.animateCamera(CameraUpdateFactory.newLatLngZoom(marker.position, 16f))

            //todo show bottom sheet
            val apartment = viewModel.apartments.value?.find { it.id == marker.id.toInt() }

            if (apartment != null) {
                BottomSheetFragment(apartment).show(supportFragmentManager, "${apartment.title}")
            }

            true
        }

        mMap!!.setOnInfoWindowClickListener {
            launchActivity<ApartmentDetailsActivity> {
                putExtra(INTENT_APARTMENT_ID, it.tag.toString())
            }
        }


        mainViewModel.apartments.observe(this, Observer { apartments ->
            for (apartment in apartments) {
                val marker = mMap!!.addMarker(
                    MarkerOptions()
                        .position(LatLng(apartment.latitude!!, apartment.longitude!!))
                        .title(apartment.title)
                )
                marker.tag = apartment.id
            }
        })

        // Prompt the user for permission.
        getLocationPermission()
        // [END_EXCLUDE]

        // Turn on the My Location layer and the related control on the map.
        updateLocationUI()

        // Get the current location of the device and set the position of the map.
        getDeviceLocation()

        mainViewModel.loadApartments()
    }

    private fun getDeviceLocation() {
        /*
         * Get the best and most recent location of the device, which may be null in rare
         * cases when a location is not available.
         */
        try {
            if (locationPermissionGranted) {
                val locationResult = fusedLocationProviderClient.lastLocation
                locationResult.addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Set the map's camera position to the current location of the device.
                        lastKnownLocation = task.result
                        if (lastKnownLocation != null) {
                            mMap?.moveCamera(
                                CameraUpdateFactory.newLatLngZoom(
                                    LatLng(
                                        lastKnownLocation!!.latitude,
                                        lastKnownLocation!!.longitude
                                    ), DEFAULT_ZOOM.toFloat()
                                )
                            )
                        }
                    } else {
                        Log.d(TAG, "Current location is null. Using defaults.")
                        Log.e(TAG, "Exception: %s", task.exception)
                        mMap?.moveCamera(
                            CameraUpdateFactory
                                .newLatLngZoom(defaultLocation, DEFAULT_ZOOM.toFloat())
                        )
                        mMap?.uiSettings?.isMyLocationButtonEnabled = false
                    }
                }
            }
        } catch (e: SecurityException) {
            Log.e("Exception: %s", e.message, e)
        }
    }

    /**
     * Prompts the user for permission to use the device location.
     */
    // [START maps_current_place_location_permission]
    private fun getLocationPermission() {
        /*
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */
        if (ContextCompat.checkSelfPermission(
                this.applicationContext,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
            == PackageManager.PERMISSION_GRANTED
        ) {
            locationPermissionGranted = true
        } else {
            ActivityCompat.requestPermissions(
                this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION
            )
        }
    }

    /**
     * Updates the map's UI settings based on whether the user has granted location permission.
     */
    // [START maps_current_place_update_location_ui]
    private fun updateLocationUI() {
        if (mMap == null) {
            return
        }
        try {
            mMap?.uiSettings?.isMapToolbarEnabled = true
            if (locationPermissionGranted) {
                mMap?.isMyLocationEnabled = true
                mMap?.uiSettings?.isMyLocationButtonEnabled = false
            } else {
                mMap?.isMyLocationEnabled = false
                mMap?.uiSettings?.isMyLocationButtonEnabled = false
                lastKnownLocation = null
                getLocationPermission()
            }
        } catch (e: SecurityException) {
            Log.e("Exception: %s", e.message, e)
        }
    }

    /**
     * Handles the result of the request for location permissions.
     */
    // [START maps_current_place_on_request_permissions_result]
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        locationPermissionGranted = false
        when (requestCode) {
            PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION -> {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.isNotEmpty() &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED
                ) {
                    locationPermissionGranted = true
                }
            }
        }
        updateLocationUI()
    }

    /**
     * Saves the state of the map when the activity is paused.
     */
    // [START maps_current_place_on_save_instance_state]
    override fun onSaveInstanceState(outState: Bundle) {
        mMap?.let { map ->
            outState.putParcelable(KEY_CAMERA_POSITION, map.cameraPosition)
            outState.putParcelable(KEY_LOCATION, lastKnownLocation)
        }
        super.onSaveInstanceState(outState)
    }

    override fun onApplyButtonClick(filter: FilterModel) {
        super.onApplyButtonClick(filter)
        viewModel.filter(filter)
    }

    override fun onResetFilterButtonClick() {
        super.onResetFilterButtonClick()
        viewModel.search("")
    }

    override fun onResume() {
        super.onResume()
        binding.mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        binding.mapView.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        binding.mapView.onLowMemory()
    }
}

private const val PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: Int = 11
private const val KEY_CAMERA_POSITION = "camera_position"
private const val KEY_LOCATION = "key_location"

private const val DEFAULT_ZOOM = 15
private const val TAG = "google map"