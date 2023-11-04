package com.example.wats_compose.data.service

interface LogService {
    fun logNonFatalCrash(throwable: Throwable)
}