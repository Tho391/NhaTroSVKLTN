package com.thomas.apps.nhatrosvkltn.view.screens.map

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker
import com.thomas.apps.nhatrosvkltn.R
import com.thomas.apps.nhatrosvkltn.model.Apartment
import com.thomas.apps.nhatrosvkltn.utils.convertPrice

class CustomInfoWindowAdapter(val context: Context) : GoogleMap.InfoWindowAdapter {
    @SuppressLint("InflateParams", "SetTextI18n")
    override fun getInfoContents(p0: Marker?): View {
        val v = LayoutInflater.from(context).inflate(R.layout.item_info_window_marker, null)

        val textViewTitle: TextView = v.findViewById(R.id.textView_title)
        val textViewAddress: TextView = v.findViewById(R.id.textView_address)
        val textViewPrice: TextView = v.findViewById(R.id.textView_price)

        val apartment = p0?.tag as Apartment

        textViewTitle.text = apartment.title
        textViewAddress.text = apartment.address
        textViewPrice.text = convertPrice(apartment.price.toString()) + " VNĐ"
        return v
    }

    @SuppressLint("InflateParams", "SetTextI18n")
    override fun getInfoWindow(p0: Marker?): View {
        val v = LayoutInflater.from(context).inflate(R.layout.item_info_window_marker, null)

        val textViewTitle: TextView = v.findViewById(R.id.textView_title)
        val textViewAddress: TextView = v.findViewById(R.id.textView_address)
        val textViewPrice: TextView = v.findViewById(R.id.textView_price)

        val apartment = p0?.tag as Apartment

        textViewTitle.text = apartment.title
        textViewAddress.text = apartment.address
        textViewPrice.text = convertPrice(apartment.price.toString()) + " VNĐ"
        return v
    }

}