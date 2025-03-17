package com.example.simplenewsflow.model

data class ByCategoryResponse(
    val sources: List<SourceByCategory>,
    val status: String
)