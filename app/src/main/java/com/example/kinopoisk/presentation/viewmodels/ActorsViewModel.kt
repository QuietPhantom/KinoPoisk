package com.example.kinopoisk.presentation.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kinopoisk.data.retrofit.Interface.RetrofitServices
import com.example.kinopoisk.data.retrofit.common.Common
import com.example.kinopoisk.data.retrofit.model.RetrofitApiCallbackEntitiesActors
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ActorsViewModel : ViewModel() {
    private lateinit var retrofitService: RetrofitServices

    val livedata = MutableLiveData<RetrofitApiCallbackEntitiesActors>()

    fun initApi(){
        retrofitService = Common.retrofitService
    }

    fun getActorsByName(keyWords: String, page: Int) {
        retrofitService.getActorsByName("8RSW3M4-PP341JX-KEQPPQH-9JFEDGZ", keyWords, "name", "false", page.toString()).enqueue(object:
            Callback<RetrofitApiCallbackEntitiesActors> {

            override fun onFailure(call: Call<RetrofitApiCallbackEntitiesActors>, t: Throwable) {
                t.printStackTrace()
            }

            override fun onResponse(call: Call<RetrofitApiCallbackEntitiesActors>, response: Response<RetrofitApiCallbackEntitiesActors>) {
                livedata.postValue(response.body()!!)
            }
        })
    }
}