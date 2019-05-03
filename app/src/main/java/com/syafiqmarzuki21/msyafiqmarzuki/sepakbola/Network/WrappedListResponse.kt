package com.syafiqmarzuki21.msyafiqmarzuki.sepakbola.Network

import com.google.gson.annotations.SerializedName

data class WrappedListResponse <T>(
    @SerializedName("status")
    val status : Int,
    @SerializedName("message")
    val message : String,
    @SerializedName("data")
    val data : List<T>
)