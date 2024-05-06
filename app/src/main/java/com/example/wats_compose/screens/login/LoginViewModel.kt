package com.example.wats_compose.screens.login

import androidx.compose.runtime.mutableStateOf
import com.example.wats_compose.HOME_SCREEN
import com.example.wats_compose.LOGIN_SCREEN
import com.example.wats_compose.R
import com.example.wats_compose.SIGN_UP_SCREEN
import com.example.wats_compose.common.ext.isValidEmail
import com.example.wats_compose.common.snackbar.SnackbarManager
import com.example.wats_compose.data.repository.AuthenticationRepository
import com.example.wats_compose.data.service.LogService
import com.example.wats_compose.screens.WatsViewModel

class LoginViewModel(
    private val authenticationRepository: AuthenticationRepository,
    logService: LogService
) : WatsViewModel(logService) {
    var uiState = mutableStateOf(LoginUiState())
        private set

    private val email
        get() = uiState.value.email
    private val password
        get() = uiState.value.password

    fun onEmailChange(newValue: String) {
        uiState.value = uiState.value.copy(email = newValue)
    }

    fun onPasswordChange(newValue: String) {
        uiState.value = uiState.value.copy(password = newValue)
    }

    fun onSignInClick(openAndPopUp: (String, String) -> Unit) {
        if (!email.isValidEmail()) {
            SnackbarManager.showMessage(R.string.email_error)
            return
        }

        if (password.isBlank()) {
            SnackbarManager.showMessage(R.string.empty_password_error)
            return
        }

        launchCatching {
            authenticationRepository.authenticate(email, password)
            openAndPopUp(HOME_SCREEN, LOGIN_SCREEN)
        }
    }


    fun onForgotPasswordClick() {
        if (!email.isValidEmail()) {
            SnackbarManager.showMessage(R.string.email_error)
            return
        }

        launchCatching {
            authenticationRepository.sendRecoveryEmail(email)
            SnackbarManager.showMessage(R.string.recovery_email_sent)
        }
    }

    fun openSignInScreen(openScreen: (String) -> Unit) {
        openScreen(SIGN_UP_SCREEN)
    }
}