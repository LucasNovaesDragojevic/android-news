package com.news.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.news.database.dao.NewsDao
import com.news.model.News

private const val DATA_BASE_NAME = "news.db"

@Database(entities = [News::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract val newsDao: NewsDao

    companion object {
        private lateinit var db: AppDatabase
        fun getInstance(context: Context): AppDatabase {
            if (::db.isInitialized)
                return db
            db = Room.databaseBuilder(context, AppDatabase::class.java, DATA_BASE_NAME).build()
            return db
        }
    }
}
