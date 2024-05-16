package com.example.wats_compose.data.repository

import com.example.wats_compose.data.model.api.MovieModel
import com.example.wats_compose.data.model.api.MovieResponseModel

interface MovieRepository {
    suspend fun getAllMovies(): MovieResponseModel?
    suspend fun getMovieById(id: String): MovieModel?
}
