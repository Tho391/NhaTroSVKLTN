package com.thomas.apps.nhatrosvkltn.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.thomas.apps.nhatrosvkltn.R
import com.thomas.apps.nhatrosvkltn.databinding.ItemApartmentBinding
import com.thomas.apps.nhatrosvkltn.model.Apartment
import com.thomas.apps.nhatrosvkltn.utils.Constant.Companion.INTENT_APARTMENT_ID
import com.thomas.apps.nhatrosvkltn.utils.TOAST
import com.thomas.apps.nhatrosvkltn.utils.launchActivity
import com.thomas.apps.nhatrosvkltn.view.screens.apartmentdetails.ApartmentDetailsActivity

class ApartmentAdapter : ListAdapter<Apartment, ApartmentAdapter.ViewHolder>(ApartmentDC()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder.from(parent)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(getItem(position))

    class ViewHolder private constructor(private val binding: ItemApartmentBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Apartment) {
            // TODO: Bind the data with View
            with(binding) {
                textViewTitle.text = item.title
                textViewAddress.text = item.address

                textViewRating.text = item.rating.toString()
                textViewPrice.text = item.price.toString()
                imageViewApartment.load(item.images?.first()?.url) {
                    placeholder(R.drawable.image_load)
                    crossfade(true)
                    size(100, 100)
                    error(R.drawable.image_broken)
                }

                root.setOnClickListener {
                    root.context.TOAST("apartment id ${item.id} clicked")

                    root.context.launchActivity<ApartmentDetailsActivity> {
                        putExtra(INTENT_APARTMENT_ID, item.id)
                    }
                }
            }
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemApartmentBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }


    private class ApartmentDC : DiffUtil.ItemCallback<Apartment>() {
        override fun areItemsTheSame(oldItem: Apartment, newItem: Apartment): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Apartment, newItem: Apartment): Boolean {
            return oldItem.id == newItem.id
        }
    }
}