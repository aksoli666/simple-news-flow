package com.example.simplenewsflow.data.api

import com.example.simplenewsflow.util.Constant.Companion.API_KEY
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsService {
    @GET("/v2/everything")
    suspend fun searchNewsByKeyword(
        @Query("q") keyword: String,
        @Query("page") page: Int = 1,
        @Query("apiKey") apiKey: String = API_KEY
    )

    @GET("/v2/everything")
    suspend fun searchNewsByCategory(
        @Query("country") country: String = "us",
        @Query("category") category: String,
        @Query("page") page: Int = 1,
        @Query("apiKey") apiKey: String = "YOUR_API_KEY"
    )
}