package com.example.kinopoisk.businesslogic.entities

import com.example.kinopoisk.data.entities.Movie

data class MovieEntity(
    val id: Int,
    val name: String,
    val year: Int,
    val description: String,
    val raiting_kp: String,
    val raiting_imdb: String
)
{
    fun toMovie() = Movie(id, name, year, description, raiting_kp, raiting_imdb)
}
