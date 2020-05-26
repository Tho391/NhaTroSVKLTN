package com.thomas.apps.nhatrosvkltn.view.screens.apartmentdetails

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import coil.api.load
import com.thomas.apps.nhatrosvkltn.R
import com.thomas.apps.nhatrosvkltn.databinding.ActivityApartmentDetailsBinding
import com.thomas.apps.nhatrosvkltn.utils.TOAST
import com.thomas.apps.nhatrosvkltn.view.adapter.CommentAdapter
import kotlinx.android.synthetic.main.activity_apartment_details.*


class ApartmentDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityApartmentDetailsBinding
    private lateinit var viewModel: ApartmentDetailsViewModel
    private var apartmentId = -1
    private val commentAdapter by lazy { CommentAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_apartment_details)
        binding = ActivityApartmentDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(ApartmentDetailsViewModel::class.java)

        init()
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun init() {
        setSupportActionBar(binding.toolBar.toolBar)

        apartmentId = intent.getIntExtra("apartmentId", -1)

        getApartment(apartmentId)
        getComments(apartmentId)



        with(binding){
            editComment.setOnTouchListener { _, event ->
                val DRAWABLE_LEFT = 0
                val DRAWABLE_TOP = 1
                val DRAWABLE_RIGHT = 2
                val DRAWABLE_BOTTOM = 3

                if (event!!.rawX >=
                    (editComment.right - editComment.compoundDrawables[DRAWABLE_RIGHT].bounds.width())
                ) {
                    TOAST("Send!")
                    edit_comment.setText("", TextView.BufferType.EDITABLE)
                }
                false
            }

            //disable toggle image

            with(cardViewUtils){
                imageWifi.isEnabled = false
                imageTime.isEnabled = false
                imageKey.isEnabled = false
                imageCar.isEnabled = false
                imageAir.isEnabled = false
                imageHeater.isEnabled = false
            }
        }

        viewModel.apartment.observe(this, Observer { apartment ->
            with(binding) {
                imageViewApartment.load(apartment.images.first().url)
                textViewTitle.text = apartment.title
                textViewDate.text = apartment.createAt
                textViewAddress.text = apartment.address
                textViewName.text = apartment.ownerName
                textViewPhone.text = apartment.phone
                rating.rating = apartment.rating
                textViewPrice.text = apartment.price.toString()
                textViewElectric.text = apartment.electric.toString()
                textViewWater.text = apartment.water.toString()
                textViewArea.text = apartment.area.toString()
                textView_details_content.text = apartment.description

                with(cardViewUtils) {
                    if (apartment.wifi) imageWifi.setChecked() else imageWifi.setUnchecked()
                    if (apartment.time) imageTime.setChecked() else imageTime.setUnchecked()
                    if (apartment.key) imageKey.setChecked() else imageKey.setUnchecked()
                    if (apartment.parking) imageCar.setChecked() else imageCar.setUnchecked()
                    if (apartment.air) imageAir.setChecked() else imageAir.setUnchecked()
                    if (apartment.heater) imageHeater.setChecked() else imageHeater.setUnchecked()
                }

                recyclerViewComments.adapter = commentAdapter
                recyclerViewComments.layoutManager =
                    LinearLayoutManager(this@ApartmentDetailsActivity)
            }
        })

        viewModel.comments.observe(this, Observer { listComments ->
            commentAdapter.submitList(listComments)
        })

    }

    private fun getComments(apartmentId: Int) {
        viewModel.getComments(apartmentId)
    }

    private fun getApartment(apartmentId: Int) {
        //TODO("Not yet implemented")
        viewModel.getApartment(apartmentId)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        //return super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.menu_apartment, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        //return super.onOptionsItemSelected(item)
        when (item.itemId) {
            R.id.action_call -> {
                TOAST("call to " + viewModel.apartment.value?.phone)
                val intent =
                    Intent(Intent.ACTION_CALL, Uri.parse("tel:" + viewModel.apartment.value?.phone))
                if (ActivityCompat.checkSelfPermission(
                        this,
                        Manifest.permission.CALL_PHONE
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                }
                startActivity(intent)
            }
            R.id.action_direction -> {
                TOAST("direction")
                val lat = viewModel.apartment.value?.latitude
                val lon = viewModel.apartment.value?.longitude
                val intent = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://www.google.com/maps/search/?api=1&query=$lat,$lon")
                )
                startActivity(intent)
            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
