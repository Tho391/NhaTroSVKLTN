package com.thomas.quickbloxchat.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.thomas.quickbloxchat.databinding.ItemChatDialogBinding
import com.thomas.quickbloxchat.model.ChatDialog
import com.thomas.quickbloxchat.screen.chat.ChatActivity

class ChatDialogAdapter :
    ListAdapter<ChatDialog, ChatDialogAdapter.ChatDialogViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ChatDialogViewHolder(ItemChatDialogBinding.inflate(LayoutInflater.from(parent.context)))

    override fun onBindViewHolder(holder: ChatDialogViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ChatDialogViewHolder(private val binding: ItemChatDialogBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(chatDialog: ChatDialog) {
            with(binding) {
                root.setOnClickListener {
                    val intent = Intent(root.context, ChatActivity::class.java).apply {
                        val arrayList = ArrayList<Int>()
                        chatDialog.occupantsIds?.let { arrayList.addAll(it) }
                        putIntegerArrayListExtra("occupantIds", arrayList)
                    }
                    root.context.startActivity(intent)
                }
                this.chatDialog = chatDialog
                executePendingBindings()
            }

        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<ChatDialog>() {
        override fun areItemsTheSame(oldItem: ChatDialog, newItem: ChatDialog): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: ChatDialog, newItem: ChatDialog): Boolean {
            return oldItem.dialogId == newItem.dialogId
        }

    }
}