package com.thomas.apps.nhatrosvkltn.view.ui.apartmentdetails

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import coil.api.load
import com.thomas.apps.nhatrosvkltn.R
import com.thomas.apps.nhatrosvkltn.databinding.ActivityApartmentDetailsBinding
import com.thomas.apps.nhatrosvkltn.utils.TOAST
import com.thomas.apps.nhatrosvkltn.view.adapter.CommentAdapter
import kotlinx.android.synthetic.main.activity_apartment_details.*
import kotlin.math.roundToInt

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

        setSupportActionBar(binding.toolBar)
        binding.toolBar.setNavigationOnClickListener {
            TOAST("back")
        }


        apartmentId = intent.getIntExtra("apartmentId", -1)

        getApartment(apartmentId)
        getComments(apartmentId)

        viewModel.apartment.observe(this, Observer { apartment ->
            with(binding) {
                imageViewApartment.load(apartment.images.first().url)
                textViewTitle.text = apartment.title
                textViewDate.text = apartment.createAt
                textViewAddress.text = apartment.address
                textViewName.text = apartment.ownerName
                textViewPhone.text = apartment.phone
                rating.numStars = apartment.rating.roundToInt()
                textViewPrice.text = apartment.price.toString()
                textViewElectric.text = apartment.electric.toString()
                textViewWater.text = apartment.water.toString()
                textViewArea.text = apartment.area.toString()
                textViewDetails.text = apartment.description
                if (apartment.wifi) image_wifi.setChecked() else image_wifi.setUnchecked()
                if (apartment.time) image_time.setChecked() else image_time.setUnchecked()
                if (apartment.key) image_key.setChecked() else image_key.setUnchecked()
                if (apartment.parking) image_car.setChecked() else image_car.setUnchecked()
                if (apartment.air) image_air.setChecked() else image_air.setUnchecked()
                if (apartment.heater) image_heater.setChecked() else image_heater.setUnchecked()
            }
        })

        viewModel.comments.observe(this, Observer { listComments ->
            commentAdapter.submitList(listComments)
        })
        binding.recyclerViewComments.adapter = commentAdapter
        binding.recyclerViewComments.layoutManager = LinearLayoutManager(this)
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
                TOAST("call")
            }
            R.id.action_direction -> {
                TOAST("direction")
            }
        }

        return super.onOptionsItemSelected(item);
    }
}
