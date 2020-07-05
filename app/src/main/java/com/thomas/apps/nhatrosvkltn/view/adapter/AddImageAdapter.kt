package com.thomas.apps.nhatrosvkltn.view.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.thomas.apps.nhatrosvkltn.R
import com.thomas.apps.nhatrosvkltn.databinding.ItemAddImageBinding
import com.thomas.apps.nhatrosvkltn.model.Image
import com.thomas.apps.nhatrosvkltn.view.screens.addapartment.AddApartmentActivity


class AddImageAdapter(val context: Context) :
    ListAdapter<Image, AddImageAdapter.ViewHolder>(ImageDC()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder.from(parent, context)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(getItem(position))

    override fun submitList(list: MutableList<Image>?) {
        super.submitList(list?.let { ArrayList(it) })
    }

    class ViewHolder private constructor(
        private val binding: ItemAddImageBinding,
        private val context: Context
    ) :
        RecyclerView.ViewHolder(binding.root) {

        val CODE_PICK_IMAGE: Int = 1001
        fun bind(item: Image) {
            with(binding) {
                if (item.url != null)
                    if (item.id == 1)
                        imageView.setImageResource(item.url.toInt())
                    else imageView.load(item.url) {
                        placeholder(R.drawable.image_load)
                        crossfade(true)
                        error(R.drawable.image_broken)
                    }

                root.setOnClickListener {
                    if (item.id == 1) {
//                        val intent = Intent().apply {
//                            type = "image/*"
//                            putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
//                            action = Intent.ACTION_GET_CONTENT;
//                        }
//                        (context as AddApartmentActivity).startActivityForResult(
//                            intent,
//                            CODE_PICK_IMAGE
//                        )
                        val intent = Intent()
                        intent.type = "image/*"
                        intent.action = Intent.ACTION_GET_CONTENT
                        (context as AddApartmentActivity).startActivityForResult(
                            Intent.createChooser(intent, "Select Picture"), CODE_PICK_IMAGE
                        )
                    }
                }

            }
        }

        companion object {
            fun from(parent: ViewGroup, context: Context): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemAddImageBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding, context)
            }
        }
    }


    private class ImageDC : DiffUtil.ItemCallback<Image>() {
        override fun areItemsTheSame(oldItem: Image, newItem: Image): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Image, newItem: Image): Boolean {
            return oldItem.id == newItem.id
        }
    }
}