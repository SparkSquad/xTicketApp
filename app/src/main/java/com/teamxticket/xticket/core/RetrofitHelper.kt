package com.teamxticket.xticket.core

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper {
    fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://172.22.32.1:3000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}