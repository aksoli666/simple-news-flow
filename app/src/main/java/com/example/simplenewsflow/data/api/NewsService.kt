package com.example.simplenewsflow.data.api

import com.example.simplenewsflow.model.ByCategoryResponse
import com.example.simplenewsflow.model.KeyWordResponse
import com.example.simplenewsflow.util.Constant.Companion.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsService {
    @GET("/v2/everything")
    suspend fun searchNewsByKeyword(
        @Query("q") q: String,
        @Query("apiKey") apiKey: String = API_KEY
    ): Response<KeyWordResponse>

    @GET("/v2/top-headlines/sources")
    suspend fun searchNewsByCategory(
        @Query("category") category: String,
        @Query("apiKey") apiKey: String = API_KEY
    ): Response<ByCategoryResponse>
}