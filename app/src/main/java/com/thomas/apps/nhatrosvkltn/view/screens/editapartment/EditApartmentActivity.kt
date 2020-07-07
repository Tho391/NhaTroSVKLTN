package com.thomas.apps.nhatrosvkltn.view.screens.editapartment

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.thomas.apps.nhatrosvkltn.R
import com.thomas.apps.nhatrosvkltn.databinding.ActivityEditApartmentBinding
import com.thomas.apps.nhatrosvkltn.model.Apartment
import com.thomas.apps.nhatrosvkltn.utils.Constant
import com.thomas.apps.nhatrosvkltn.utils.getUser
import com.thomas.apps.nhatrosvkltn.view.adapter.ImageAdapter

class EditApartmentActivity : AppCompatActivity() {

    private lateinit var viewModel: EditApartmentViewModel
    private lateinit var binding: ActivityEditApartmentBinding
    private val districts: List<String> =
        resources.getStringArray(R.array.districts).toList().drop(1)
    private var adapter = ImageAdapter()
    private var lat = 0.0
    private var lon = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditApartmentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(EditApartmentViewModel::class.java)

        init()
    }

    private fun init() {
        setSupportActionBar(binding.toolBar.toolBar)


        val districts: List<String> = resources.getStringArray(R.array.districts).toList()
        binding.cardViewInfo.spinnerDistrict.setItems(districts)

        val apartment = intent.getSerializableExtra(Constant.INTENT_APARTMENT) as Apartment
        updateUI(apartment)
    }

    private fun updateUI(apartment: Apartment) {
        with(binding) {
            with(cardViewInfo) {
                editTextTitle.setText(apartment.title)
                editTextName.setText(apartment.ownerName)
                editTextPhone.setText(apartment.phone)
                editTextAddress.setText(apartment.address)
                spinnerDistrict.selectedIndex = districts.indexOf(apartment.district)
                editTextPrice.setText(apartment.price.toString())
                editTextArea.setText(apartment.area.toString())
                editTextElectric.setText(apartment.electric.toString())
                editTextWater.setText(apartment.water.toString())
            }
            with(cardViewUtils) {
                if (apartment.wifi) imageWifi.setChecked() else imageWifi.setUnchecked()
                if (apartment.time) imageTime.setChecked() else imageTime.setUnchecked()
                if (apartment.key) imageKey.setChecked() else imageKey.setUnchecked()
                if (apartment.parking) imageCar.setChecked() else imageCar.setUnchecked()
                if (apartment.air) imageAir.setChecked() else imageAir.setUnchecked()
                if (apartment.heater) imageHeater.setChecked() else imageHeater.setUnchecked()
            }

            editTextDetailsContent.setText(apartment.description)
            recyclerView.apply {
                layoutManager = LinearLayoutManager(this@EditApartmentActivity)
                adapter = this@EditApartmentActivity.adapter
            }
        }
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
                val user = getUser(this)
                if (user != null && !user.hasToken()) {
                    val apartment = getData()

                    viewModel.editApartment(user.getToken(), apartment)
                }

            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun getData(): Apartment {
        return with(binding) {
            Apartment(
                cardViewInfo.editTextTitle.text.toString(),
                cardViewInfo.editTextAddress.editableText.toString(),
                districts[cardViewInfo.spinnerDistrict.selectedIndex],
                lat,
                lon,
                editTextDetailsContent.editableText.toString(),
                cardViewInfo.editTextName.editableText.toString(),
                cardViewInfo.editTextPhone.editableText.toString(),
                cardViewInfo.editTextPrice.toString().toLong(),
                cardViewInfo.editTextElectric.toString().toInt(),
                cardViewInfo.editTextWater.editableText.toString().toInt(),
                cardViewInfo.editTextArea.editableText.toString().toInt(),
                cardViewUtils.imageWifi.getState() == 1,
                cardViewUtils.imageTime.getState() == 1,
                cardViewUtils.imageKey.getState() == 1,
                cardViewUtils.imageCar.getState() == 1,
                cardViewUtils.imageAir.getState() == 1,
                cardViewUtils.imageHeater.getState() == 1,
                null,
                null
            )
        }
    }
}
