package com.raineyi.moviekp.data.database

import android.app.Application
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.raineyi.moviekp.data.network.model.DescriptionDto

@Database(entities = [MoviesDao::class], [DescriptionDto::class], version = 1, exportSchema = false)
abstract class MovieDatabase () : RoomDatabase() {

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

    abstract fun MoviesDao(): MoviesDao
}