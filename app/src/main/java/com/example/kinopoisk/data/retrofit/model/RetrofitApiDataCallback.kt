package com.example.kinopoisk.data.retrofit.model

data class RetrofitApiCallbackEntities(
    val docs: List<RetrofitApiDataEntity>,
    val total: Int,
    val page: Int,
    val pages: Int
)

data class RetrofitApiDataEntity(
    val id: Int,
    val type: String,
    val name: String,
    val description: String,
    val year: String,
    val alternativeName: String,
    val movieLength: String,
    val poster: PosterEntity,
    val rating: RatingEntity,
    val votes: VotesEntity,
    val externalId: ExternalIdEntity
)

data class PosterEntity(
    val url: String,
    val previewUrl: String
)

data class RatingEntity(
    val kp: String,
    val imdb: String,
    val filmCritics: String,
    val russianFilmCritics: String,
    val await: String
)

data class VotesEntity(
    val kp: String,
    val imdb: String,
    val filmCritics: String,
    val russianFilmCritics: String,
    val await: String
)

data class ExternalIdEntity(
    val kpHD: String,
    val imdb: String,
    val tmdb: String,
)