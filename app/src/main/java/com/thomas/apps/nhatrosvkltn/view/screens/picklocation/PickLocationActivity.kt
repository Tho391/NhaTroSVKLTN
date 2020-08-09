package com.thomas.apps.nhatrosvkltn.view.screens.picklocation

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
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
import com.thomas.apps.nhatrosvkltn.R
import com.thomas.apps.nhatrosvkltn.databinding.ActivityPickLocationBinding
import com.thomas.apps.nhatrosvkltn.utils.TOAST

class PickLocationActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var viewModel: PickLocationViewModel
    private lateinit var binding: ActivityPickLocationBinding
    private lateinit var mMap: GoogleMap
    private var lat: Double = 0.0
    private var lon: Double = 0.0
    private val DEFAULT_ZOOM = 15F

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPickLocationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(PickLocationViewModel::class.java)

        init(savedInstanceState)
    }

    private fun init(savedInstanceState: Bundle?) {
        setSupportActionBar(binding.toolBar.toolBar)

        Dexter.withContext(this)
            .withPermissions(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
            .withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                    report?.let {
                        if (report.areAllPermissionsGranted()) {
                            TOAST("OK")
                            binding.mapView.getMapAsync(this@PickLocationActivity)
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

        binding.buttonLocation.setOnClickListener {
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(LatLng(lat, lon), DEFAULT_ZOOM))
        }
        binding.mapView.onCreate(savedInstanceState)

    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.uiSettings.isMyLocationButtonEnabled = true
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        mMap.isMyLocationEnabled = true

        mMap.uiSettings.isMapToolbarEnabled = false

        mMap.setOnMapLongClickListener {
            mMap.clear()
            lat = it.latitude
            lon = it.longitude
            val markerOptions = MarkerOptions().position(it)
            mMap.addMarker(markerOptions)
        }

        val sydney = LatLng(10.825455, 106.692307)
        mMap.addMarker(
            MarkerOptions().position(sydney)
                .title("Marker in Sydney")
        )
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney, 15F))
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

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_pick_location, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        //return super.onOptionsItemSelected(item)
        when (item.itemId) {
            R.id.action_pick_location -> {

                val returnIntent = Intent()
                returnIntent.putExtra("lat", lat)
                returnIntent.putExtra("lon", lon)
                setResult(Activity.RESULT_OK, returnIntent)
                finish()
            }
        }

        return super.onOptionsItemSelected(item)
    }
}
