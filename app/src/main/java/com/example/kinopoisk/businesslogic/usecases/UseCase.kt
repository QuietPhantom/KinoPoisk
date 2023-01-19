package com.example.kinopoisk.businesslogic.usecases

import com.example.kinopoisk.businesslogic.entities.MovieEntity
import com.example.kinopoisk.businesslogic.iRepositories.IMovieRepository

class UseCase (private val movieRepository: IMovieRepository) : IUseCase {
    override suspend fun getAllMovies(): List<MovieEntity>
            = movieRepository.getAllMovies()

    override suspend fun getMovieById(id: Int): MovieEntity
            = movieRepository.getMovieById(id)

    override suspend fun getCountMovieById(id: Int): Int
            = movieRepository.getCountMovieById(id)

    override suspend fun saveMovie(title: MovieEntity)
            = movieRepository.saveMovie(title)

    override suspend fun deleteMovie(title: MovieEntity)
            = movieRepository.deleteMovie(title)

}