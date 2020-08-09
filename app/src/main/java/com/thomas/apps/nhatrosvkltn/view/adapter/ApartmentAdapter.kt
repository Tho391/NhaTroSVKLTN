package com.thomas.apps.nhatrosvkltn.view.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import coil.size.Scale
import com.thomas.apps.nhatrosvkltn.R
import com.thomas.apps.nhatrosvkltn.databinding.ItemApartmentBinding
import com.thomas.apps.nhatrosvkltn.model.Apartment
import com.thomas.apps.nhatrosvkltn.utils.Constant.Companion.INTENT_APARTMENT_ID
import com.thomas.apps.nhatrosvkltn.utils.convertPrice
import com.thomas.apps.nhatrosvkltn.utils.launchActivity
import com.thomas.apps.nhatrosvkltn.view.screens.apartmentdetails.ApartmentDetailsActivity

class ApartmentAdapter : ListAdapter<Apartment, ApartmentAdapter.ViewHolder>(ApartmentDC()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder.from(parent)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(getItem(position))

    class ViewHolder private constructor(private val binding: ItemApartmentBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(item: Apartment) {
            with(binding) {
                textViewTitle.text = item.title

                val districts: List<String> =
                    binding.root.context.resources.getStringArray(R.array.districts).toList()

                textViewAddress.text = item.address + ", " + districts[item.districtId]

                textViewRating.text = item.rating.toString()

                textViewPrice.text = convertPrice(item.price.toString()) + " VNĐ"

//                textViewPrice.text = item.price.toString()
                imageViewApartment.load(item.images?.first()?.url) {
                    placeholder(R.drawable.image_load)
                    crossfade(true)
                    scale(Scale.FIT)
                    size(100, 100)
                    error(R.drawable.image_broken)
                }

                root.setOnClickListener {
                    //root.context.TOAST("apartment id ${item.id} clicked")

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