package com.example.kinopoisk.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.kinopoisk.businesslogic.entities.MovieEntity
import com.example.kinopoisk.businesslogic.usecases.IUseCase
import com.example.kinopoisk.data.Application
import com.example.kinopoisk.data.retrofit.Interface.RetrofitServices
import com.example.kinopoisk.data.retrofit.common.Common
import com.example.kinopoisk.data.retrofit.model.RetrofitApiDataCallbackMovie
import com.example.kinopoisk.data.retrofit.model.RetrofitApiDataEntityMovie
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieElementViewModel : ViewModel() {
    private lateinit var retrofitService: RetrofitServices
    private lateinit var UseCase : IUseCase

    val livedata = MutableLiveData<RetrofitApiDataCallbackMovie>()

    fun initApi(application: Application){
        retrofitService = Common.retrofitService
        UseCase = application.getUseCase()
    }

    fun getMovieById(MovieId: Int) {
        retrofitService.getMovieBuId("8RSW3M4-PP341JX-KEQPPQH-9JFEDGZ", MovieId.toString(), "id").enqueue(object:
            Callback<RetrofitApiDataCallbackMovie> {
            override fun onFailure(call: Call<RetrofitApiDataCallbackMovie>, t: Throwable) {
                t.printStackTrace()
            }

            override fun onResponse(call: Call<RetrofitApiDataCallbackMovie>, response: Response<RetrofitApiDataCallbackMovie>) {
                livedata.postValue(response.body()!!)
            }
        })
    }

    suspend fun saveMovie(movie: MovieEntity){
        UseCase.saveMovie(movie)
    }

    suspend fun deleteMovie(movie: MovieEntity){
        UseCase.deleteMovie(movie)
    }

    fun getCountMovieById(id: Int): LiveData<Int> = liveData {
        emit(UseCase.getCountMovieById(id))
    }
}