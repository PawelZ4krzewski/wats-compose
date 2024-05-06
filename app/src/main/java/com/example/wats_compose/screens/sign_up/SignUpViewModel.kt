package com.example.wats_compose.screens.sign_up

import androidx.compose.runtime.mutableStateOf
import com.example.wats_compose.HOME_SCREEN
import com.example.wats_compose.SIGN_UP_SCREEN
import com.example.wats_compose.common.ext.isValidEmail
import com.example.wats_compose.common.ext.isValidPassword
import com.example.wats_compose.common.ext.passwordMatches
import com.example.wats_compose.common.snackbar.SnackbarManager
import com.example.wats_compose.data.repository.AuthenticationRepository
import com.example.wats_compose.data.service.LogService
import com.example.wats_compose.screens.WatsViewModel
import com.example.wats_compose.R.string as AppText


class SignUpViewModel(
    private val authenticationRepository: AuthenticationRepository,
    logService: LogService
) : WatsViewModel(logService) {
    var uiState = mutableStateOf(SignUpUiState())
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

    fun onRepeatPasswordChange(newValue: String) {
        uiState.value = uiState.value.copy(repeatPassword = newValue)
    }

    fun onSignUpClick(openAndPopUp: (String, String) -> Unit) {
        if (!email.isValidEmail()) {
            SnackbarManager.showMessage(AppText.email_error)
            return
        }

        if (!password.isValidPassword()) {
            SnackbarManager.showMessage(AppText.password_error)
            return
        }

        if (!password.passwordMatches(uiState.value.repeatPassword)) {
            SnackbarManager.showMessage(AppText.password_match_error)
            return
        }

        launchCatching {
            authenticationRepository.linkAccount(email, password)
            authenticationRepository.createAccount(
                email = email,
                password = password,
                onSucess = {
                    openAndPopUp(HOME_SCREEN, SIGN_UP_SCREEN)
                },
                onFailure = { })

        }
    }
}