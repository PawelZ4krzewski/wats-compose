package com.example.wats_compose.screens.home

import com.example.wats_compose.SPLASH_SCREEN
import com.example.wats_compose.data.repository.AuthenticationRepository
import com.example.wats_compose.data.service.LogService
import com.example.wats_compose.screens.WatsViewModel

class HomeViewModel(
    private val authenticationRepository: AuthenticationRepository,
    logService: LogService
) : WatsViewModel(logService) {

    fun onSignOutClick(restartApp: (String) -> Unit) {
        launchCatching {
            authenticationRepository.signOut()
            restartApp(SPLASH_SCREEN)
        }
    }
}