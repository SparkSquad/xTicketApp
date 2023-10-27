package com.teamxticket.xticket.core

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper {
    fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://192.168.100.81:3000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}