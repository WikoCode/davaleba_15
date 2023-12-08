package com.example.myapplication

import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.databinding.ItemMessagesBinding

class MessagesAdapter : ListAdapter<MessagesItem, MessagesAdapter.MessageViewHolder>(
    MessageViewHolder.MessagesDiffCallback()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val binding =
            ItemMessagesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MessageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val message = getItem(position)
        holder.bind(message)
    }

    class MessageViewHolder(private val binding: ItemMessagesBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(message: MessagesItem) {
            binding.apply {
                tvFullName.text = message.owner
                tvLastMessage.text = message.last_message
                tvLastActive.text = message.last_active



                tvLastMessage.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)

                when (message.laste_message_type) {
                    "voice" -> {
                        tvLastMessage.setCompoundDrawablesWithIntrinsicBounds(
                            R.drawable.ic_voicemessage,
                            0,
                            0,
                            0
                        )
                        tvLastMessage.text = "Sent a voice message"
                    }

                    "file" -> {
                        tvLastMessage.setCompoundDrawablesWithIntrinsicBounds(
                            R.drawable.ic_filemessage,
                            0,
                            0,
                            0
                        )
                        tvLastMessage.text = "Sent a file message"
                    }
                }

                when (message.is_typing) {
                    true -> {
                        ivMessageState.setImageResource(R.drawable.ic_typing)
                    }

                    false -> {
                        ivMessageState.setImageDrawable(null)
                    }

                }

                if (message.unread_messages > 0) {
                    ivMessageState.setImageResource(R.drawable.ic_green_circle)
                    tvUnreadMessages.text = message.unread_messages.toString()

                    tvLastMessage.setTypeface(null, Typeface.BOLD)
                } else {
                    tvLastMessage.setTypeface(null, Typeface.NORMAL)
                }

                Glide.with(root)
                    .load(message.image.takeIf { it?.isNotBlank() == true } ?: R.drawable.default_profile_image)
                    .circleCrop()
                    .into(ivIcon)


            }
        }

        class MessagesDiffCallback : DiffUtil.ItemCallback<MessagesItem>() {
            override fun areItemsTheSame(oldItem: MessagesItem, newItem: MessagesItem): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: MessagesItem, newItem: MessagesItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}