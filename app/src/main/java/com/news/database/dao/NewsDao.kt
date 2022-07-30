package com.news.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.news.model.News

@Dao
interface NewsDao {

    @Query("SELECT * FROM News ORDER BY id DESC")
    fun findAll(): LiveData<List<News>>

    @Query("SELECT * FROM News WHERE id = :id")
    fun findById(id: Long): LiveData<News?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(News: News)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(News: List<News>)

    @Delete
    fun remove(News: News)
}
