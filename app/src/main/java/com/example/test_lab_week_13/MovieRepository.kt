package com.example.test_lab_week_13

import com.example.test_lab_week_13.api.MovieService
import com.example.test_lab_week_13.model.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import android.util.Log
import com.example.test_lab_week_13.database.MovieDatabase
import com.example.test_lab_week_13.database.MovieDao

class MovieRepository(
    private val movieService: MovieService,
    private val movieDatabase: MovieDatabase) {

    private val apiKey = "5757f2ea957d7564cd7ab05530e2383d"

    fun fetchMovies(): Flow<List<Movie>> = flow {
        val dao = movieDatabase.movieDao()

        try {
            val response = movieService.getPopularMovies(apiKey)
            val movies = response.results

            // save to room
            dao.addMovies(movies)

            emit(movies)
        } catch (e: Exception) {
            Log.e("MovieRepo", "API error, loading from Room", e)
            val cached = dao.getMovies()
            emit(cached)
        }
    }.flowOn(Dispatchers.IO)
}
