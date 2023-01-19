package com.example.kinopoisk.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.kinopoisk.data.entities.Movie

@Database(entities = [Movie::class], version = 1)
abstract class KinoPoiskDatabase : RoomDatabase() {

    abstract fun getDAO() : DAO

}