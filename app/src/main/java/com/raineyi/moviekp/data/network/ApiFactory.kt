package com.raineyi.moviekp.data.network

import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object ApiFactory {

    private const val BASE_URL = "https://kinopoiskapiunofficial.tech/api/v2.2/"

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        //
        .baseUrl(BASE_URL)
        .build()

    //  .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
    val apiService = retrofit.create(ApiService::class.java)
}