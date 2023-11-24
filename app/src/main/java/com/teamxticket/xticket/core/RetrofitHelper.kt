package com.teamxticket.xticket.core

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper {
    fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://xticket.vadam.xyz/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    //  .baseUrl("https://xticket.vadam.xyz/")
}
