package com.syafiqmarzuki21.msyafiqmarzuki.sepakbola.Network

import com.google.gson.annotations.SerializedName

data class WrappedResponse <T>(
    @SerializedName("status")
    val status : Int,
    @SerializedName("message")
    val message : String,
    @SerializedName("data")
    val data : T
)