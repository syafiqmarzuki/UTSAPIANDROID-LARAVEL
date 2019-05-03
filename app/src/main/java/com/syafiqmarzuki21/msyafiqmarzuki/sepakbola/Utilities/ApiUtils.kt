package com.syafiqmarzuki21.msyafiqmarzuki.sepakbola.Utilities

import com.syafiqmarzuki21.msyafiqmarzuki.sepakbola.Service.PemainService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApiUtils {
    companion object {
        private val API_URL = "https://apibola.herokuapp.com/api/"
        private var retrofit: Retrofit? = null
        private var okHttpClient = OkHttpClient.Builder().connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS).writeTimeout(30, TimeUnit.SECONDS).build()

        fun pemService(): PemainService = getClient().create(PemainService::class.java)

        fun getClient() : Retrofit{
            return if (retrofit == null){
                retrofit = Retrofit.Builder().baseUrl(API_URL).client(okHttpClient).addConverterFactory(GsonConverterFactory.create()).build()
                retrofit!!
            }else{ retrofit!! }
        }
    }
}