package com.example.wats_compose.data.remote

object HttpRoutes {
    private const val BASE_URL = "http://10.0.2.2:3000"
    const val ALL_MOVIE = "$BASE_URL/movie"
    const val MOVIE_BY_ID = "$BASE_URL/movie/{id}"
}