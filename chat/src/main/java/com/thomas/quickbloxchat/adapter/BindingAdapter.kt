package com.thomas.quickbloxchat.adapter

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import coil.api.load
import com.thomas.quickbloxchat.R
import java.text.SimpleDateFormat
import java.util.*

@BindingAdapter("imageUrl")
fun bindImage(imageView: ImageView, imageUrl: String?) {
    if (imageUrl.isNullOrEmpty())
        imageView.load(R.drawable.ic_baseline_chat_24)
    else
        imageView.load(imageUrl) {
            placeholder(R.drawable.ic_baseline_chat_24)
        }
}

@BindingAdapter("date")
fun bindDate(textView: TextView, date: Long) {
    val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    textView.text = dateFormat.format(Date(date * 1000)).toString()
}

@BindingAdapter("lastMessage")
fun bindLastMessage(textView: TextView, lastMessage: String?) {
    if (lastMessage.isNullOrEmpty()) {
        textView.text = "Attachment"
    } else textView.text = lastMessage
}