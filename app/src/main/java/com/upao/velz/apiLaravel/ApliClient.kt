package com.upao.velz.apiLaravel

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Apiclient {

    private val gson: Gson = GsonBuilder()
        .setLenient()
        .create()

    private val retrofit: Retrofit = Retrofit.Builder()
        //.baseUrl("http://3.141.15.88/api/")
        .baseUrl("http://192.168.0.14:8000/api/")
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    fun <T> createService(serviceClass: Class<T>): T {
        return retrofit.create(serviceClass)
    }
}