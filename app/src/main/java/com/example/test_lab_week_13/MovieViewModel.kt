package com.example.test_lab_week_13

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.test_lab_week_13.model.Movie
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import android.util.Log

class MovieViewModel(private val movieRepository: MovieRepository) : ViewModel() {

    private val _popularMovies = MutableStateFlow<List<Movie>>(emptyList())
    val popularMovies: StateFlow<List<Movie>> = _popularMovies

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    init {
        Log.d("VM_CHECK", "ViewModel WEEK 13 STARTED")
        fetchPopularMovies()
    }


    private fun fetchPopularMovies() {
        viewModelScope.launch {
            movieRepository.fetchMovies()
                .catch { e -> _error.value = e.message ?: "Unknown error" }
                .collect { movies ->
                    _popularMovies.value = movies

                    movies.forEach {
                        Log.d("POSTER_TEST", "PosterPath = ${it.posterPath}")
                    }
                }
        }
    }
}
