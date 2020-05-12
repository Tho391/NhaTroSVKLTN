package com.thomas.apps.nhatrosvkltn.view.adapter

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.View.OnClickListener
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import coil.api.load
import com.thomas.apps.nhatrosvkltn.R
import com.thomas.apps.nhatrosvkltn.databinding.ItemCommentBinding
import com.thomas.apps.nhatrosvkltn.model.Comment

class CommentAdapter : ListAdapter<Comment, CommentAdapter.ViewHolder>(CommentDC()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder.from(parent)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(getItem(position))

    class ViewHolder private constructor(private val binding: ItemCommentBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Comment) {
            // TODO: Bind the data with View
            with(binding){
                imageViewAvatar.load(item.user.avatar){
                    placeholder(R.drawable.image_load)
                    crossfade(true)
                    error(R.drawable.image_broken)
                }
                textViewAuthor.text = item.user.name
                textViewContent.text = item.content
                textViewDate.text = item.createdAt
            }
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemCommentBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }


    private class CommentDC : DiffUtil.ItemCallback<Comment>() {
        override fun areItemsTheSame(oldItem: Comment, newItem: Comment): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Comment, newItem: Comment): Boolean {
            return oldItem == newItem
        }
    }
}