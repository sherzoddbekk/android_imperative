package com.example.android_imperative.db

import android.content.Context
import androidx.core.content.ContextCompat
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.android_imperative.model.TvShow

@Database(entities = [TvShow::class], version = 2, exportSchema = false)
abstract class Appdatabase : RoomDatabase() {
    abstract fun getTVShowDao(): TVshowDao

    companion object {
        @Volatile
        private var DB_INSTANCE: Appdatabase? = null

        fun getAppDBInstance(context: Context): Appdatabase {
            if (DB_INSTANCE == null) {
                DB_INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    Appdatabase::class.java,
                    "DB_TV_SHOWS"
                )
                    .allowMainThreadQueries()
                    .build()
            }
            return DB_INSTANCE!!
        }
    }
}