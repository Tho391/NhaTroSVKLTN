package com.thomas.apps.nhatrosvkltn.view.screens.map

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker
import com.thomas.apps.nhatrosvkltn.R

class CustomInfoWindowAdapter(val context: Context) : GoogleMap.InfoWindowAdapter {
    override fun getInfoContents(p0: Marker?): View {
        val v = LayoutInflater.from(context).inflate(R.layout.item_info_window_marker, null)

        val textViewTitle: TextView = v.findViewById(R.id.textView_title)
        val textViewAddress: TextView = v.findViewById(R.id.textView_address)
        val textViewPrice: TextView = v.findViewById(R.id.textView_price)

        textViewTitle.text = p0?.title

        return v
    }

    override fun getInfoWindow(p0: Marker?): View {
        return LayoutInflater.from(context).inflate(R.layout.item_info_window_marker, null)
    }

}