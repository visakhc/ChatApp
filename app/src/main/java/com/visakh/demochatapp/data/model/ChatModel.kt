package com.visakh.demochatapp.data.model

class ChatModel(
    val message: String,
    val mime: String, // image, text or image_text
    val image: String="",
    val msgDirection: String, // send or received
    val time: String
)