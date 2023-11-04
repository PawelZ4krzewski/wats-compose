package com.example.wats_compose.data.module

import com.example.wats_compose.data.repository.AuthenticationRepository
import com.example.wats_compose.data.repositoryImpl.AuthenticationRepositoryImpl
import com.example.wats_compose.data.service.LogService
import com.example.wats_compose.data.serviceImpl.LogServiceImpl
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import org.koin.dsl.module

val firebaseModule = module {
    single {
        Firebase.auth
    }

    single<AuthenticationRepository> {
        AuthenticationRepositoryImpl(get())
    }

    single<LogService> {
        LogServiceImpl()
    }
}