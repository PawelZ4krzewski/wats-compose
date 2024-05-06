package com.example.wats_compose.screens.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.wats_compose.common.composable.BasicButton
import com.example.wats_compose.common.composable.BasicTextButton
import com.example.wats_compose.common.composable.BasicToolbar
import com.example.wats_compose.common.composable.EmailField
import com.example.wats_compose.common.composable.PasswordField
import com.example.wats_compose.common.ext.basicButton
import com.example.wats_compose.common.ext.fieldModifier
import com.example.wats_compose.common.ext.textButton
import com.example.wats_compose.theme.WatsTheme
import org.koin.androidx.compose.koinViewModel
import com.example.wats_compose.R.string as AppText

@Composable
fun LoginScreen(
    openAndPopUp: (String, String) -> Unit,
    openScreen: (String) -> Unit,
    viewModel: LoginViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState

    LoginScreenContent(
        uiState = uiState,
        onEmailChange = viewModel::onEmailChange,
        onPasswordChange = viewModel::onPasswordChange,
        onSignInClick = { viewModel.onSignInClick(openAndPopUp) },
        openScreen = { viewModel.openSignInScreen(openScreen) },
        onForgotPasswordClick = viewModel::onForgotPasswordClick
    )
}

@Composable
fun LoginScreenContent(
    modifier: Modifier = Modifier,
    uiState: LoginUiState,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onSignInClick: () -> Unit,
    openScreen: () -> Unit,
    onForgotPasswordClick: () -> Unit
) {
    BasicToolbar(AppText.login_details)
    Column(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        EmailField(uiState.email, onEmailChange, Modifier.fieldModifier())
        PasswordField(uiState.password, onPasswordChange, Modifier.fieldModifier())

        BasicButton(AppText.sign_in, Modifier.basicButton()) { onSignInClick() }
        BasicButton(AppText.create_account, Modifier.basicButton()) { openScreen() }

        BasicTextButton(AppText.forgot_password, Modifier.textButton()) {
            onForgotPasswordClick()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    val uiState = LoginUiState(
        email = "email@test.com"
    )

    WatsTheme {
        LoginScreenContent(
            uiState = uiState,
            onEmailChange = { },
            onPasswordChange = { },
            onSignInClick = { },
            openScreen = { },
            onForgotPasswordClick = { }
        )
    }
}
