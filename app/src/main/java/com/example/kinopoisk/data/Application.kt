package com.example.kinopoisk.data

import android.app.Application
import androidx.room.Room
import com.example.kinopoisk.businesslogic.usecases.UseCase
import com.example.kinopoisk.businesslogic.usecases.IUseCase
import com.example.kinopoisk.data.database.KinoPoiskDatabase
import com.example.kinopoisk.data.repositories.MovieRepository

class Application : Application() {

    private lateinit var database : KinoPoiskDatabase

    override fun onCreate() {
        super.onCreate()
        database = Room.databaseBuilder(
            applicationContext,
            KinoPoiskDatabase::class.java,
            "kinopoisk-database"
        ).build()
    }

    private var noteUseCase : IUseCase? = null

    fun getUseCase() : IUseCase {
        if (noteUseCase == null){
            noteUseCase = UseCase(MovieRepository(database.getDAO()))
        }
        return noteUseCase!!
    }

}