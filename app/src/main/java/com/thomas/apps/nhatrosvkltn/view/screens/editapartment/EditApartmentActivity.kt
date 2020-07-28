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
    private lateinit var districts: List<String>
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

        districts =
            resources.getStringArray(R.array.districts).toList().drop(1)

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
                spinnerDistrict.selectedIndex = apartment.districtId - 1
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
                title = cardViewInfo.editTextTitle.text.toString(),
                address = cardViewInfo.editTextAddress.editableText.toString(),
                district = districts[cardViewInfo.spinnerDistrict.selectedIndex],
                latitude = lat,
                longitude = lon,
                description = editTextDetailsContent.editableText.toString(),
                ownerName = cardViewInfo.editTextName.editableText.toString(),
                phone = cardViewInfo.editTextPhone.editableText.toString(),
                price = cardViewInfo.editTextPrice.toString().toInt(),
                electric = cardViewInfo.editTextElectric.toString().toInt(),
                water = cardViewInfo.editTextWater.editableText.toString().toInt(),
                area = cardViewInfo.editTextArea.editableText.toString().toInt(),
                wifi = cardViewUtils.imageWifi.getState() == 1,
                time = cardViewUtils.imageTime.getState() == 1,
                key = cardViewUtils.imageKey.getState() == 1,
                parking = cardViewUtils.imageCar.getState() == 1,
                air = cardViewUtils.imageAir.getState() == 1,
                heater = cardViewUtils.imageHeater.getState() == 1,
                id = 0,
                images = null,
                user = null,
                districtId = cardViewInfo.spinnerDistrict.selectedIndex + 1,
                rating = 0F,
                createAt = ""
            )
        }
    }
}
