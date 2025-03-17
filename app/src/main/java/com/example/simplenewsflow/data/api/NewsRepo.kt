package com.example.simplenewsflow.data.api

import javax.inject.Inject

class NewsRepo @Inject constructor(private val newsService: NewsService) {
    suspend fun searchNewsByKeyword(q: String) =
        newsService.searchNewsByKeyword(q)

    suspend fun  searchNewsByCategory(category: String) =
        newsService.searchNewsByCategory(category = category)
}