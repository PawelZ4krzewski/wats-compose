package com.example.wats_compose.data.repository

import com.example.wats_compose.data.model.UserModel
import kotlinx.coroutines.flow.Flow

interface AuthenticationRepository {

    val currentUserId: String
    val hasUser: Boolean

    val currentUser: Flow<UserModel>

    suspend fun authenticate(email: String, password: String)
    suspend fun sendRecoveryEmail(email: String)
    suspend fun linkAccount(email: String, password: String)
    suspend fun createAccount(email: String, password: String, onSucess: () -> Unit, onFailure: () -> Unit)
    suspend fun deleteAccount()
    suspend fun signOut()
    suspend fun getAccessToken(onSuccess: (String) -> Unit)
}