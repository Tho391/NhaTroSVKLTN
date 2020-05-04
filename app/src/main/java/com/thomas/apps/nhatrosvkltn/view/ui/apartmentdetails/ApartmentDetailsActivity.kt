package com.thomas.apps.nhatrosvkltn.view.ui.apartmentdetails

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.thomas.apps.nhatrosvkltn.R
import com.thomas.apps.nhatrosvkltn.databinding.ActivityApartmentDetailsBinding
import com.thomas.apps.nhatrosvkltn.utils.*
class ApartmentDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityApartmentDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_apartment_details)
        binding = ActivityApartmentDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)


        setSupportActionBar(binding.toolBar)
        binding.toolBar.setNavigationOnClickListener{
            TOAST("back")
        }
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
