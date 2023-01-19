package com.example.kinopoisk.data.retrofit.Interface

import com.example.kinopoisk.data.retrofit.model.RetrofitApiCallbackEntitiesActors
import com.example.kinopoisk.data.retrofit.model.RetrofitApiCallbackEntitiesMovies
import com.example.kinopoisk.data.retrofit.model.RetrofitApiDataCallbackMovie
import retrofit2.Call
import retrofit2.http.*

interface RetrofitServices {

    @GET("/movie")
    fun getMoviesOrSeries(@Query("token") token: String, @Query("search") search: String, @Query("field") field: String, @Query("isStrict") isStrict: String, @Query("page") page: String): Call<RetrofitApiCallbackEntitiesMovies>

    @GET("/person")
    fun getActorsByName(@Query("token") token: String, @Query("search") search: String, @Query("field") field: String, @Query("isStrict") isStrict: String, @Query("page") page: String): Call<RetrofitApiCallbackEntitiesActors>

    @GET("/movie")
    fun getMovieBuId(@Query("token") token: String, @Query("search") search: String, @Query("field") field: String): Call<RetrofitApiDataCallbackMovie>
}