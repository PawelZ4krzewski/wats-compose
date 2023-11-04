package com.example.wats_compose.data.module

import com.example.wats_compose.screens.WatsViewModel
import com.example.wats_compose.screens.login.LoginViewModel
import com.example.wats_compose.screens.splash.SplashViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    viewModel {
        WatsViewModel(get())
    }

    viewModel {
        LoginViewModel(get(), get())
    }

    viewModel {
        SplashViewModel(get(), get())
    }
}