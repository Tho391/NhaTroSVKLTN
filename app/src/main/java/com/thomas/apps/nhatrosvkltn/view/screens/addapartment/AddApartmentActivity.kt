package com.thomas.apps.nhatrosvkltn.view.screens.addapartment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.thomas.apps.nhatrosvkltn.R
import com.thomas.apps.nhatrosvkltn.databinding.ActivityAddApartmentBinding
import com.thomas.apps.nhatrosvkltn.model.Apartment
import com.thomas.apps.nhatrosvkltn.model.Image
import com.thomas.apps.nhatrosvkltn.utils.Data.Companion.images1
import com.thomas.apps.nhatrosvkltn.utils.Data.Companion.user1
import com.thomas.apps.nhatrosvkltn.utils.TOAST
import com.thomas.apps.nhatrosvkltn.utils.getUser
import com.thomas.apps.nhatrosvkltn.utils.launchActivity
import com.thomas.apps.nhatrosvkltn.view.adapter.AddImageAdapter
import com.thomas.apps.nhatrosvkltn.view.screens.picklocation.PickLocationActivity
import java.io.File


class AddApartmentActivity : AppCompatActivity() {

    private val STATE_CHECKED = 1
    val PICK_LOCATION: Int by lazy { 1000 }
    val CODE_PICK_IMAGE: Int = 1001

    private lateinit var viewModel: AddApartmentViewModel
    private lateinit var binding: ActivityAddApartmentBinding
    private var lat: Double = 0.0
    private var lng: Double = 0.0

    private lateinit var adapter: AddImageAdapter
    private var listImage: ArrayList<Image> =
        arrayListOf(Image(1, R.drawable.ic_baseline_add_a_photo_24.toString()))
    private var imageId = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddApartmentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(AddApartmentViewModel::class.java)

        init()
    }


    private fun init() {
        setSupportActionBar(binding.toolBar.toolBar)

        supportActionBar?.title = "Đăng phòng"

        val districts: List<String> = resources.getStringArray(R.array.districts).toList().drop(1)
        binding.cardViewInfo.spinnerDistrict.setItems(districts)

        binding.cardViewInfo.buttonPickLocation.setOnClickListener {
            launchActivity<PickLocationActivity>(PICK_LOCATION) {
            }
        }

        adapter = AddImageAdapter(this)
        binding.recyclerView.adapter = adapter
        val layoutManager = LinearLayoutManager(this)
        val dividerItemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.addItemDecoration(dividerItemDecoration)
        adapter.submitList(listImage)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                PICK_LOCATION -> {
                    //todo get lat lng here
                    if (data != null) {
                        lat = data.getDoubleExtra("lat", 0.0)
                        lng = data.getDoubleExtra("lng", 0.0)
                    }
                }
                CODE_PICK_IMAGE -> {
                    if (listImage.size < 5) {
                        if (data != null) {
                            //val imageUri = getRealPathFromURI(this, data.data!!)
                            val uri = data.dataString
                            TOAST("$uri ")

                            listImage.add(Image(++imageId, uri))
                            adapter.submitList(listImage)
                        }
                    } else TOAST("Tối đa 5 hình")
                }
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
        return when (item.itemId) {
            R.id.action_pick_location -> {
                //todo post apartment here
                val apartment = getData()
                val user = getUser(this)
                if (user != null) {
                    if (!user.hasToken()) {
                        //todo request user login
                    } else {
                        val file = File(listImage.first().url)
                        viewModel.postApartment(user.getToken(), file, apartment)
                    }
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun getData(): Apartment {
        val title = binding.cardViewInfo.editTextTitle.text.toString()
        val owner = binding.cardViewInfo.editTextName.text.toString()
        val phone = binding.cardViewInfo.editTextPhone.text.toString()
        val address = binding.cardViewInfo.editTextAddress.text.toString()
        val price = binding.cardViewInfo.editTextPrice.text.toString()
        val area = binding.cardViewInfo.editTextArea.text.toString()
        val district = binding.cardViewInfo.spinnerDistrict.text.toString()
        val electric = binding.cardViewInfo.editTextElectric.text.toString()
        val water = binding.cardViewInfo.editTextWater.text.toString()


        val wifi = binding.cardViewUtils.imageWifi.getState() == STATE_CHECKED
        val time = binding.cardViewUtils.imageTime.getState() == STATE_CHECKED
        val key = binding.cardViewUtils.imageKey.getState() == STATE_CHECKED
        val car = binding.cardViewUtils.imageCar.getState() == STATE_CHECKED
        val air = binding.cardViewUtils.imageAir.getState() == STATE_CHECKED
        val heater = binding.cardViewUtils.imageHeater.getState() == STATE_CHECKED
        val description = binding.editTextDetailsContent.text.toString()


        return Apartment(
            title,
            address,
            district,
            lat,
            lng,
            description,
            owner,
            phone,
            price.toLong(),
            electric.toInt(),
            water.toInt(),
            area.toInt(),
            wifi,
            time,
            key,
            car,
            air,
            heater,
            images1,
            user1
        )
    }
}
