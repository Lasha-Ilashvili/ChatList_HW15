package com.example.chatlist_hw15.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.chatlist_hw15.model.Chat
import com.example.chatlist_hw15.databinding.ChatItemBinding

class ChatItemAdapter : ListAdapter<Chat, ChatItemAdapter.ChatItemViewHolder>(ChatITemDiffUtil) {

    object ChatITemDiffUtil : DiffUtil.ItemCallback<Chat>() {
        override fun areItemsTheSame(oldItem: Chat, newItem: Chat): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Chat, newItem: Chat): Boolean {
            return oldItem == newItem
        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatItemViewHolder {
        return ChatItemViewHolder(
            ChatItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ChatItemViewHolder, position: Int) {
        holder.bind()
    }

    inner class ChatItemViewHolder(private val binding: ChatItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind() {
            val chat = currentList[adapterPosition]

            with(binding) {
                tvOwner.text = chat.owner
                tvLastMessage.text = chat.lastMessage
                tvLastActive.text = chat.lastActive
                tvUnreadMessages.text = chat.unreadMessages.toString()
            }
        }
    }
}