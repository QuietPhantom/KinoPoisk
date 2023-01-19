package com.example.kinopoisk.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.kinopoisk.businesslogic.entities.MovieEntity

@Entity
data class Movie(
    @PrimaryKey val id: Int,
    val name: String,
    val year: Int,
    val description: String,
    val raiting_kp: String,
    val raiting_imdb: String
)
{
    fun toMovieEntity() = MovieEntity(id, name, year, description, raiting_kp, raiting_imdb)
}
