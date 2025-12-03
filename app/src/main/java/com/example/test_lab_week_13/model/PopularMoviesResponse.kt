package com.example.test_lab_week_13.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PopularMoviesResponse(
    val page: Int? = null,
    val results: List<Movie> = emptyList(),
    val total_results: Int? = null,
    val total_pages: Int? = null
)
