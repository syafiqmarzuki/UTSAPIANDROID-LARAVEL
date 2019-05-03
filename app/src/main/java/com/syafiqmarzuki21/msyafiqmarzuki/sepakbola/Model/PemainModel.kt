package com.syafiqmarzuki21.msyafiqmarzuki.sepakbola.Model
import com.google.gson.annotations.SerializedName

data class PemainModel (@SerializedName("id") var id : Int?,
                        @SerializedName("name") var name : String?,
                        @SerializedName("description") var description : String?,
                        @SerializedName("image") var image : String?
) {
    constructor() : this(null, null, null, null )
}