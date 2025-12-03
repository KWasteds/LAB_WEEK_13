package com.example.test_lab_week_13

import android.app.Application
import com.example.test_lab_week_13.api.MovieService
import com.example.test_lab_week_13.database.MovieDatabase
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.example.test_lab_week_13.MovieRepository
import com.example.test_lab_week_13.MovieWorker
import androidx.work.*
import java.util.concurrent.TimeUnit

class MovieApplication : Application() {
    lateinit var movieRepository: MovieRepository
    override fun onCreate() {
        super.onCreate()

        val moshi = com.squareup.moshi.Moshi.Builder()
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()

        val movieService = retrofit.create(
            MovieService::class.java
        )

        val movieDatabase =
            MovieDatabase.getInstance(applicationContext)

        movieRepository =
            MovieRepository(movieService, movieDatabase)

        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val workRequest = PeriodicWorkRequest
            .Builder(
                MovieWorker::class.java, 1,
                TimeUnit.HOURS
            ).setConstraints(constraints)
            .addTag("movie-work").build()

        WorkManager.getInstance(
            applicationContext
        ).enqueue(workRequest)
    }
}
