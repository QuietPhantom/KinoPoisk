package com.example.kinopoisk.data.retrofit.model

import java.util.Date

data class RetrofitApiDataCallbackMovie(
    val id: Int,
    val type: String,
    val name: String,
    val description: String,
    val slogan: String,
    val year: Int,
    val poster: PosterEntity,
    val rating: RatingEntity,
    val votes: VotesEntity,
    val budget: BudgetEntity,
    val fees: FeesEntity,
    val status: String,
    val movieLength: String,
    val genres: List<GenresEntity>,
    val ratingMpaa: String,
    val ageRating: String,
    val createDate: String
)

data class BudgetEntity(
    val value: String,
    val currency: String
)

data class FeesEntity(
    val world: WorldFeesEntity
)

data class WorldFeesEntity(
    val value: String,
    val currency: String
)

data class GenresEntity(
    val name: String
)

