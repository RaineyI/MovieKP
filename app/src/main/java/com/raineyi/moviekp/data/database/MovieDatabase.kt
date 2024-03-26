package com.raineyi.moviekp.data.database

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.raineyi.moviekp.data.database.dbmodel.DescriptionDbModel
import com.raineyi.moviekp.data.database.dbmodel.MovieDbModel

@Database(entities = [MovieDbModel::class, DescriptionDbModel::class], version = 1, exportSchema = false)
@TypeConverters(DataConverter::class)
abstract class MovieDatabase : RoomDatabase() {

    companion object {
        @Volatile
        private var db: MovieDatabase? = null
        private const val DB_NAME = "movie_db"
        private val LOCK = Any()

        fun getInstance(application: Application): MovieDatabase {
            synchronized(LOCK) {
                db?.let { return it }
                val instance = Room.databaseBuilder(
                    application,
                    MovieDatabase::class.java,
                    DB_NAME
                ).build()
                db = instance
                return instance
            }
        }
    }
    abstract fun moviesDao(): MovieDao
}