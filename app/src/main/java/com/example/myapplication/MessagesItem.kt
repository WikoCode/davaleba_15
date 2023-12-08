package com.example.myapplication

data class MessagesItem(
    val id: Int,
    val image: String?,
    val is_typing: Boolean,
    val last_active: String,
    val last_message: String,
    val laste_message_type: String,
    val owner: String,
    val unread_messages: Int
)