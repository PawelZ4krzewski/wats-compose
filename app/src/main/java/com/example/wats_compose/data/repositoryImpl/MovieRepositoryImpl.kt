package com.example.wats_compose.data.repositoryImpl

import android.util.Log
import com.example.wats_compose.data.model.api.MovieModel
import com.example.wats_compose.data.model.api.MovieResponseModel
import com.example.wats_compose.data.remote.HttpRoutes
import com.example.wats_compose.data.repository.MovieRepository
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.RedirectResponseException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.request.get

class MovieRepositoryImpl(
    private val client: HttpClient
): MovieRepository {
    override suspend fun getAllMovies(): MovieResponseModel? {
        return try {
            val data = client.get(HttpRoutes.ALL_MOVIE)
            Log.d("TAG", "getAllMovies: ${data}")
            data.body()
        } catch(e: RedirectResponseException) {
            // 3xx - responses
            Log.d("TAG","Error: ${e.response.status.description}")
            null
        } catch(e: ClientRequestException) {
            // 4xx - responses
            Log.d("TAG","Error: ${e.response.status.description}")
            null
        } catch(e: ServerResponseException) {
            // 5xx - responses
            Log.d("TAG","Error: ${e.response.status.description}")
            null
        } catch(e: Exception) {
            Log.d("TAG","Error: ${e.message}")
            null
        }
    }
    override suspend fun getMovieById(id: String): MovieModel? {
        return try {
            Log.d("TAG", "getMovieById: ${HttpRoutes.MOVIE_BY_ID.replace("{id}", id)}")
            val data = client.get(HttpRoutes.MOVIE_BY_ID.replace("{id}", id))
            Log.d("TAG", "getMovieById: ${data}")
            data.body()
        } catch(e: RedirectResponseException) {
            // 3xx - responses
            Log.d("TAG","Error: ${e.response.status.description}")
            null
        } catch(e: ClientRequestException) {
            // 4xx - responses
            Log.d("TAG","Error: ${e.response.status.description}")
            null
        } catch(e: ServerResponseException) {
            // 5xx - responses
            Log.d("TAG","Error: ${e.response.status.description}")
            null
        } catch(e: Exception) {
            Log.d("TAG","Error: ${e.message}")
            null
        }    }
}