package com.example.wats_compose.data.model.api

import kotlinx.serialization.Serializable

@Serializable
data class MovieResponseModel(
    val items: List<MovieModel>?,
    val count: Int?
)
