package com.example.kinopoisk.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.kinopoisk.data.entities.Movie

@Dao
interface DAO {
    @Query("Select * From movie")
    suspend fun getAllMovies() : List<Movie>

    @Query("Select * From movie Where id = :id")
    suspend fun getMovieById(id: Int) : Movie

    @Query("Select count(*) From movie Where id = :id")
    suspend fun getCountMovieById(id: Int) : Int

    @Insert
    suspend fun saveMovie(movie: Movie)

    @Delete
    suspend fun deleteMovie(movie: Movie)
}