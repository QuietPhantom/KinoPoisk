package com.example.kinopoisk.presentation.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kinopoisk.data.retrofit.Interface.RetrofitServices
import com.example.kinopoisk.data.retrofit.common.Common
import com.example.kinopoisk.data.retrofit.model.RetrofitApiCallbackEntities
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieViewModel : ViewModel() {
    private lateinit var retrofitService: RetrofitServices

    val livedata = MutableLiveData<RetrofitApiCallbackEntities>()

    fun initApi(){
        retrofitService = Common.retrofitService
    }

    fun getMoviesOrSeries(keyWords: String, page: Int) {
        retrofitService.getMoviesOrSeries("8RSW3M4-PP341JX-KEQPPQH-9JFEDGZ", keyWords, "name", "false", page.toString()).enqueue(object:
            Callback<RetrofitApiCallbackEntities> {

            override fun onFailure(call: Call<RetrofitApiCallbackEntities>, t: Throwable) {
                t.printStackTrace()
            }

            override fun onResponse(call: Call<RetrofitApiCallbackEntities>, response: Response<RetrofitApiCallbackEntities>) {
                livedata.postValue(response.body()!!)
            }
        })
    }
}