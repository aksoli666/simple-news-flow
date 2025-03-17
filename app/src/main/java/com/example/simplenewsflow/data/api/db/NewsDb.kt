package com.example.simplenewsflow.data.api.db

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

abstract class NewsDb: RoomDatabase() {

    companion object {
        @Volatile
        private var instance: NewsDb? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance?: synchronized(LOCK) {
            instance?: createDatabase(context).also {
                instance = it
            }
        }

        private fun createDatabase(context: Context): NewsDb {
            return Room.databaseBuilder(
                context.applicationContext,
                NewsDb::class.java,
                name = "news_db"
            ).build()
        }
    }
}