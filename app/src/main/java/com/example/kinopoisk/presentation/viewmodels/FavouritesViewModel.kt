package com.example.kinopoisk.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.kinopoisk.businesslogic.entities.MovieEntity
import com.example.kinopoisk.businesslogic.usecases.IUseCase
import com.example.kinopoisk.data.Application

class FavouritesViewModel : ViewModel() {
    private lateinit var UseCase : IUseCase

    fun init(application: Application){
        UseCase = application.getUseCase()
    }

    fun getTitles(): LiveData<List<MovieEntity>> = liveData {
        emit(UseCase.getAllMovies())
    }

    suspend fun saveTitle(movie: MovieEntity){
        UseCase.saveMovie(movie)
    }

    suspend fun deleteTitle(movie: MovieEntity){
        UseCase.deleteMovie(movie)
    }
}