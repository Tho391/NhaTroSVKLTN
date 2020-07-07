package com.thomas.apps.nhatrosvkltn.view.screens.map


import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.thomas.apps.nhatrosvkltn.databinding.FragmentBottomsheetBinding
import com.thomas.apps.nhatrosvkltn.model.Apartment
import com.thomas.apps.nhatrosvkltn.utils.TOAST


class BottomSheetFragment(val apartment: Apartment) : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentBottomsheetBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentBottomsheetBinding.inflate(layoutInflater)

        with(binding) {
            if (apartment.rating != null) {
                ratingBar.rating = apartment.rating
                textViewRating.text = apartment.rating.toString()
            }
            textViewTitle.text = apartment.title

            buttonCall.setOnClickListener {
                if (!apartment.phone.isNullOrEmpty()) {
                    TOAST("call to " + apartment.phone)
                    val intent =
                        Intent(Intent.ACTION_CALL, Uri.parse("tel:" + apartment.phone))
                    if (ActivityCompat.checkSelfPermission(
                            binding.root.context,
                            Manifest.permission.CALL_PHONE
                        ) != PackageManager.PERMISSION_GRANTED
                    ) {
                        startActivity(intent)
                    }
                }

            }

            buttonDirection.setOnClickListener {
                val lat = apartment.latitude
                val lon = apartment.longitude
                if (lat != null && lon != null) {
                    val intent = Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://www.google.com/maps/search/?api=1&query=$lat,$lon")
                    )
                    startActivity(intent)
                }
            }
        }


        return view
    }
}