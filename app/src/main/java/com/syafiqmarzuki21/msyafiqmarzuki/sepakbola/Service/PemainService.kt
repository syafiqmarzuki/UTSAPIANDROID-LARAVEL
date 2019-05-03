package com.syafiqmarzuki21.msyafiqmarzuki.sepakbola.Service

import com.syafiqmarzuki21.msyafiqmarzuki.sepakbola.Model.PemainModel
import com.syafiqmarzuki21.msyafiqmarzuki.sepakbola.Network.WrappedListResponse
import com.syafiqmarzuki21.msyafiqmarzuki.sepakbola.Network.WrappedResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*
interface PemainService {
    @GET("bola")
    fun all() : Call<WrappedListResponse<PemainModel>>

    @GET("bola/{id}")
    fun find(@Path("id") id : String) : Call<WrappedResponse<PemainModel>>

    @Multipart
    @POST("bola/create")
    fun new(@Part image : MultipartBody.Part, @Part("name") name : RequestBody, @Part("description") description : RequestBody) : Call<WrappedResponse<PemainModel>>

    @Multipart
    @POST("bola/{id}")
    fun update(@Part image : MultipartBody.Part, @Part("name") name : RequestBody, @Part("description") description : RequestBody, @Path("id") id : String) : Call<WrappedResponse<PemainModel>>

    @DELETE("bola/{id}")
    fun delete(@Path("id") id : String) : Call<WrappedResponse<PemainModel>>

    @FormUrlEncoded
    @POST("bola/search/result")
    fun search(@Field("search") search : String) : Call<WrappedListResponse<PemainModel>>
}