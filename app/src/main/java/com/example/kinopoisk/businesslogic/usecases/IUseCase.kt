package com.example.kinopoisk.businesslogic.usecases

import com.example.kinopoisk.businesslogic.entities.MovieEntity

interface IUseCase {
    suspend fun getAllMovies(): List<MovieEntity>
    suspend fun getMovieById(id: Int) : MovieEntity
    suspend fun getCountMovieById(id: Int) : Int
    suspend fun saveMovie(title: MovieEntity)
    suspend fun deleteMovie(title: MovieEntity)
}