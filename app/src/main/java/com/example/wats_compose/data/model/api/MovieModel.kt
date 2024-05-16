package com.example.wats_compose.data.model.api

import kotlinx.serialization.Serializable

@Serializable
data class MovieModel (
    val id: String,
    val year: Int?,
    val genres: List<String>?,
    val description: String?,
    val title: String?,
    val platform: String?,
    val thumbnailUrl: String?,
)