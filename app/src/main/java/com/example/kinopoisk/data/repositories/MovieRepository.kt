package com.example.kinopoisk.data.repositories

import com.example.kinopoisk.businesslogic.entities.MovieEntity
import com.example.kinopoisk.businesslogic.iRepositories.IMovieRepository
import com.example.kinopoisk.data.database.DAO

class MovieRepository(private val DAO: DAO) : IMovieRepository {

    override suspend fun getAllMovies(): List<MovieEntity> {
        return DAO.getAllMovies().map {it.toMovieEntity()}
    }

    override suspend fun getMovieById(id: Int): MovieEntity {
        return DAO.getMovieById(id).toMovieEntity()
    }

    override suspend fun getCountMovieById(id: Int): Int {
        return DAO.getCountMovieById(id)
    }

    override suspend fun saveMovie(title: MovieEntity) {
        DAO.saveMovie(title.toMovie())
    }

    override suspend fun deleteMovie(title: MovieEntity) {
        DAO.deleteMovie(title.toMovie())
    }
}