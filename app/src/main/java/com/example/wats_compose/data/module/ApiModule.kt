package com.example.wats_compose.data.module

import com.example.wats_compose.core.KtorClient
import com.example.wats_compose.data.repository.MovieRepository
import com.example.wats_compose.data.repositoryImpl.MovieRepositoryImpl
import org.koin.dsl.module

val apiModule = module {
    single {
        KtorClient.httpClient
    }
    single<MovieRepository> {
        MovieRepositoryImpl(get())
    }
}