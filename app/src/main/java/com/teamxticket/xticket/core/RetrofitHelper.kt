package com.teamxticket.xticket.core

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper {
    fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("aquivaelurl")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}