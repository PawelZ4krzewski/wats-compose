package com.example.wats_compose.screens.home

import androidx.compose.material.AlertDialog
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.wats_compose.common.composable.BasicButton
import com.example.wats_compose.common.composable.DialogCancelButton
import com.example.wats_compose.common.composable.DialogConfirmButton
import com.example.wats_compose.common.ext.basicButton
import org.koin.androidx.compose.koinViewModel
import com.example.wats_compose.R.string as AppText

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(
    restartApp: (String) -> Unit,
    viewModel: HomeViewModel = koinViewModel()
) {
    SignOutCard {
        viewModel.onSignOutClick(restartApp)
    }
}

@ExperimentalMaterialApi
@Composable
private fun SignOutCard(signOut: () -> Unit) {
    var showWarningDialog by remember { mutableStateOf(false) }

    BasicButton(text = AppText.sign_out, modifier = Modifier.basicButton()) {
        showWarningDialog = true
    }

    if (showWarningDialog) {
        AlertDialog(
            title = { Text(stringResource(AppText.sign_out_title)) },
            text = { Text(stringResource(AppText.sign_out_description)) },
            dismissButton = { DialogCancelButton(AppText.cancel) { showWarningDialog = false } },
            confirmButton = {
                DialogConfirmButton(AppText.sign_out) {
                    signOut()
                    showWarningDialog = false
                }
            },
            onDismissRequest = { showWarningDialog = false }
        )
    }
}