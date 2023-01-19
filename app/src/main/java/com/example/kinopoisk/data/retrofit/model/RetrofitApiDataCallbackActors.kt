package com.example.kinopoisk.data.retrofit.model

data class RetrofitApiCallbackEntitiesActors(
    val docs: List<RetrofitApiDataEntityActor>,
    val total: Int,
    val page: Int,
    val pages: Int
)

data class RetrofitApiDataEntityActor(
    val id: Int,
    val name: String,
    val enName: String,
    val photo: String,
    val age: String,
    val sex: String
)
