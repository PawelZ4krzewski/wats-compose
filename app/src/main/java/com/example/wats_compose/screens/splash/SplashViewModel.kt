package com.example.wats_compose.screens.splash

import androidx.compose.runtime.mutableStateOf
import com.example.wats_compose.HOME_SCREEN
import com.example.wats_compose.LOGIN_SCREEN
import com.example.wats_compose.SPLASH_SCREEN
import com.example.wats_compose.data.repository.AuthenticationRepository
import com.example.wats_compose.data.service.LogService
import com.example.wats_compose.screens.WatsViewModel

class SplashViewModel(
    private val authenticationRepository: AuthenticationRepository,
    logService: LogService
) : WatsViewModel(logService) {
    val showError = mutableStateOf(false)

    fun onAppStart(openAndPopUp: (String, String) -> Unit) {

        showError.value = false
        if (authenticationRepository.hasUser) openAndPopUp(HOME_SCREEN, SPLASH_SCREEN)
        else openAndPopUp(LOGIN_SCREEN, SPLASH_SCREEN)
    }
}