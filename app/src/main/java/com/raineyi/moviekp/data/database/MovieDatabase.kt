package com.raineyi.moviekp.data.database

import android.app.Application
import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

abstract class MovieDatabase (
    application: Application
) : RoomDatabase() {

    companion object {
        private var db: MovieDatabase? = null
        private const val DB_NAME = "movie_db"
        private val LOCK = Any()

        fun getInstance(context: Context): MovieDatabase {
            synchronized(LOCK) {
                db?.let { return it }
                val instance: MovieDatabase = Room.databaseBuilder(
                    context,
                    MovieDatabase::class.java,
                    DB_NAME
                ).build()
                db = instance
                return instance
            }
        }
    }
}