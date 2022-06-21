package com.visakh.demochatapp.data.model

import com.google.gson.annotations.SerializedName

data class Response(

    @field:SerializedName("data")
    val data: Data? = null,

    @field:SerializedName("status")
    val status: Boolean? = null
)

data class Data(

    @field:SerializedName("timeStamp")
    val timeStamp: String? = null,

    @field:SerializedName("id")
    val id: String? = null,

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("time")
    val time: String? = null,

    @field:SerializedName("imageUrl")
    val imageUrl: String? = null,

    @field:SerializedName("mime")
    val mime: String? = null
)
