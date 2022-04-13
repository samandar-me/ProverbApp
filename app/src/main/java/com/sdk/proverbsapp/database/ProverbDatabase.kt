package com.sdk.proverbsapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.sdk.proverbsapp.model.Proverb
import com.sdk.proverbsapp.util.Utils.DATABASE_NAME

@Database(entities = [Proverb::class], version = 1, exportSchema = false)
abstract class ProverbDatabase : RoomDatabase() {

    abstract fun mainDao(): MainDao

    companion object {
        private var database: ProverbDatabase? = null

        @Synchronized
        fun getInstance(context: Context): ProverbDatabase {
            if (database == null) {
                database = Room.databaseBuilder(
                    context.applicationContext,
                    ProverbDatabase::class.java, DATABASE_NAME
                )
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return database!!
        }
    }
}