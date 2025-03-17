package com.example.simplenewsflow.model

data class KeyWordResponse(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)