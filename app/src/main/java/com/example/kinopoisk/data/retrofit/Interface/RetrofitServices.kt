package com.example.kinopoisk.data.retrofit.Interface

import com.example.kinopoisk.data.retrofit.model.RetrofitApiCallbackEntities
import retrofit2.Call
import retrofit2.http.*

interface RetrofitServices {

    @GET("/movie")
    fun getMoviesOrSeries(@Query("token") token: String, @Query("search") search: String, @Query("field") field: String, @Query("isStrict") isStrict: String, @Query("page") page: String): Call<RetrofitApiCallbackEntities>

}