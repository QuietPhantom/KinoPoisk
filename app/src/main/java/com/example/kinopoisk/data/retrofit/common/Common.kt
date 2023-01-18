package com.example.kinopoisk.data.retrofit.common

import com.example.kinopoisk.data.retrofit.Interface.RetrofitServices
import com.example.kinopoisk.data.retrofit.`object`.RetrofitClient

object Common {
    private val BASE_URL = "https://api.kinopoisk.dev"
    val retrofitService: RetrofitServices
        get() = RetrofitClient.getClient(BASE_URL).create(RetrofitServices::class.java)
}