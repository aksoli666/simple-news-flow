package com.example.simplenewsflow.data.api.db

import androidx.lifecycle.LiveData
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.simplenewsflow.model.Article

interface NewsDao {
    @Query("SELECT * FROM articles")
    suspend fun getAllNewsByKeyword(): LiveData<List<Article>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(article: Article)

    @Delete
    suspend fun delete(article: Article)
}