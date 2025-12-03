package com.example.test_lab_week_13

import com.example.test_lab_week_13.api.MovieService
import com.example.test_lab_week_13.model.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import android.util.Log

class MovieRepository(private val movieService: MovieService) {

    private val apiKey = "5757f2ea957d7564cd7ab05530e2383d"

    fun fetchMovies(): Flow<List<Movie>> = flow {
        try {
            val response = movieService.getPopularMovies(apiKey)
            Log.d("MovieRepo", "Movies fetched: ${response.results.size}")
            emit(response.results)
        } catch (e: Exception) {
            Log.e("MovieRepo", "Failed to fetch movies", e)
            throw e
        }
    }.flowOn(Dispatchers.IO)
}
