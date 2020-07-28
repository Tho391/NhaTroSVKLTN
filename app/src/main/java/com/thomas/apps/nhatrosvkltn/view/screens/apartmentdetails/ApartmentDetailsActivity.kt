package com.thomas.apps.nhatrosvkltn.view.screens.apartmentdetails

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import coil.api.load
import com.google.android.gms.ads.AdRequest
import com.thomas.apps.nhatrosvkltn.R
import com.thomas.apps.nhatrosvkltn.databinding.ActivityApartmentDetailsBinding
import com.thomas.apps.nhatrosvkltn.model.servermodel.CommentResponse
import com.thomas.apps.nhatrosvkltn.utils.TOAST
import com.thomas.apps.nhatrosvkltn.utils.getUser
import com.thomas.apps.nhatrosvkltn.utils.launchActivity
import com.thomas.apps.nhatrosvkltn.view.adapter.CommentAdapter
import com.thomas.apps.nhatrosvkltn.view.screens.listimage.ListImagesActivity
import com.thomas.quickbloxchat.screen.main.MainActivity
import kotlinx.android.synthetic.main.activity_apartment_details.*
import java.text.SimpleDateFormat
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
                    val user = getUser(this@ApartmentDetailsActivity)
                    if (user != null && user.hasToken()
                        && editComment.text.toString().isNotEmpty()
                    ) {
                        val commentResponse = CommentResponse(
                            idNhatro = this@ApartmentDetailsActivity.apartmentId,
                            userId = user.id,
                            content = editComment.text.toString(),
                            date = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(
                                Date()
                            )
                        )
                        viewModel.sendComment(
                            user.getToken(),
                            commentResponse
                        )
                        editComment.text.clear()
                    }


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
                        if (user != null && user.hasToken() && apartmentId != -1)
                            viewModel.rating(
                                user.getToken(),
                                apartmentId,
                                CommentResponse(vote = rating, userId = user.id)
                            )
                    }
                }
            }
        }

        viewModel.apartment.observe(this, Observer { apartment ->
            with(binding) {
                imageViewApartment.load(apartment.images?.first()?.url)
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

        viewModel.postSuccess.observe(this, Observer {
            TOAST("Sent")
            if (it) viewModel.getComments(apartmentId)
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
//                TOAST("call to " + viewModel.apartment.value?.phone)
//                val intent =
//                    Intent(Intent.ACTION_CALL, Uri.parse("tel:" + viewModel.apartment.value?.phone))
//                if (ActivityCompat.checkSelfPermission(
//                        this,
//                        Manifest.permission.CALL_PHONE
//                    ) == PackageManager.PERMISSION_GRANTED
//                ) {
//                    startActivity(intent)
//                } else {
//                    ActivityCompat.requestPermissions(
//                        this, arrayOf(Manifest.permission.CALL_PHONE),
//                        PERMISSIONS_REQUEST_CALL
//                    )
//                }

                val userId: Int? = getUser(this)?.id
                when {
                    userId != null -> {
                        val user = userId % 2 + 1
                        launchActivity<MainActivity> {
                            putExtra("userId", user)
                        }
                    }
                    userId == null -> {
                        TOAST("Đăng nhập để thực hiện chức năng này!")
                    }

                }
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

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            PERMISSIONS_REQUEST_CALL -> {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.isNotEmpty() &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED
                ) {
                }
            }
        }
    }
}

private const val PERMISSIONS_REQUEST_CALL: Int = 11
