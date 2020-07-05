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
import com.google.android.gms.ads.AdRequest
import com.thomas.apps.nhatrosvkltn.R
import com.thomas.apps.nhatrosvkltn.databinding.ActivityApartmentDetailsBinding
import com.thomas.apps.nhatrosvkltn.model.Comment
import com.thomas.apps.nhatrosvkltn.utils.*
import com.thomas.apps.nhatrosvkltn.view.adapter.CommentAdapter
import com.thomas.apps.nhatrosvkltn.view.screens.listimage.ListImagesActivity
import kotlinx.android.synthetic.main.activity_apartment_details.*
import java.util.*


class ApartmentDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityApartmentDetailsBinding
    private lateinit var viewModel: ApartmentDetailsViewModel
    private var apartmentId = -1
    private val commentAdapter by lazy { CommentAdapter() }
    private var currentTimeRating = System.currentTimeMillis()
    private var currentTimeComment = System.currentTimeMillis()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_apartment_details)
        binding = ActivityApartmentDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(ApartmentDetailsViewModel::class.java)

        loadAds()

        init()
    }

    private fun loadAds() {
        val adRequest = AdRequest.Builder().build()
        binding.adView.loadAd(adRequest)

    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.onDestroy()
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun init() {
        binding.progressBar.hide()
        setSupportActionBar(binding.toolBar.toolBar)

        apartmentId = intent.getIntExtra("apartmentId", -1)

        with(binding) {
            editComment.setOnTouchListener { _, event ->
                val DRAWABLE_LEFT = 0
                val DRAWABLE_TOP = 1
                val DRAWABLE_RIGHT = 2
                val DRAWABLE_BOTTOM = 3

                if (event!!.rawX >=
                    (editComment.right - editComment.compoundDrawables[DRAWABLE_RIGHT].bounds.width())
                ) {
                    TOAST("Send!")
                    editComment.setText("", TextView.BufferType.EDITABLE)
                    //todo call api send comment

                    val token = getToken(this@ApartmentDetailsActivity)
                    if (token.isEmpty()) {
                        //todo request user login
                    } else
                        viewModel.sendComment(
                            token,
                            Comment(
                                1,
                                editComment.text.toString(),
                                Date().toString(),
                                Data.user1,
                                Data.a1
                            )
                        )
                }
                false
            }

            //disable toggle image

            with(cardViewUtils) {
                imageWifi.isEnabled = false
                imageTime.isEnabled = false
                imageKey.isEnabled = false
                imageCar.isEnabled = false
                imageAir.isEnabled = false
                imageHeater.isEnabled = false
            }

            swipeRefreshLayout.setOnRefreshListener {
                getApartment(apartmentId)
                getComments(apartmentId)
            }
            swipeRefreshLayout.post {
                getApartment(apartmentId)
                getComments(apartmentId)
            }

            rating.setOnRatingBarChangeListener { ratingBar, rating, fromUser ->
                val time = System.currentTimeMillis()
                if (time - currentTimeRating > 3000) {
                    currentTimeRating = time
                    if (fromUser) {
                        val user = getUser(this@ApartmentDetailsActivity)
                        if (user != null && !user.token.isNullOrEmpty() && apartmentId != -1)
                            viewModel.rating(user.token, apartmentId, rating)
                    }
                }
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
                textViewRating.text = apartment.rating.toString()
                //rating.rating = apartment.rating
                textViewPrice.text = apartment.price.toString()
                textViewElectric.text = apartment.electric.toString()
                textViewWater.text = apartment.water.toString()
                textViewArea.text = apartment.area.toString()
                textViewDetailsContent.text = apartment.description

                with(cardViewUtils) {
                    if (apartment.wifi) imageWifi.setChecked() else imageWifi.setUnchecked()
                    if (apartment.time) imageTime.setChecked() else imageTime.setUnchecked()
                    if (apartment.key) imageKey.setChecked() else imageKey.setUnchecked()
                    if (apartment.parking) imageCar.setChecked() else imageCar.setUnchecked()
                    if (apartment.air) imageAir.setChecked() else imageAir.setUnchecked()
                    if (apartment.heater) imageHeater.setChecked() else imageHeater.setUnchecked()
                }

                binding.imageViewApartment.setOnClickListener {
                    //todo pass images object to image activity
                    launchActivity<ListImagesActivity> {
//                        putExtra("id", apartment.id)
                        putExtra("apartmentId", apartment.id)

                    }
                }

                recyclerViewComments.adapter = commentAdapter
                recyclerViewComments.layoutManager =
                    LinearLayoutManager(this@ApartmentDetailsActivity)
            }
        })

        viewModel.comments.observe(this, Observer { listComments ->
            commentAdapter.submitList(listComments)
        })

        viewModel.isApartmentLoading.observe(this, Observer {
            swipeRefreshLayout.isRefreshing = it
        })

        viewModel.isPosting.observe(this, Observer {
            if (it) binding.progressBar.show()
            else binding.progressBar.hide()
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
