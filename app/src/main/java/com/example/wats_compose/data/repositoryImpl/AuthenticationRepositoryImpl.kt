package com.example.wats_compose.data.repositoryImpl

import android.util.Log
import com.example.wats_compose.data.model.UserModel
import com.example.wats_compose.data.repository.AuthenticationRepository
import com.example.wats_compose.data.service.trace
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class AuthenticationRepositoryImpl(
    private val auth: FirebaseAuth
): AuthenticationRepository {

    override val currentUserId: String
        get() = auth.currentUser?.uid.orEmpty()
    override val hasUser: Boolean
        get() = auth.currentUser != null
    override val currentUser: Flow<UserModel>
        get() = callbackFlow {
            val listener =
                FirebaseAuth.AuthStateListener { auth ->
                    this.trySend(auth.currentUser?.let { UserModel(it.uid, it.isAnonymous) } ?: UserModel())
                }
            auth.addAuthStateListener(listener)
            awaitClose { auth.removeAuthStateListener(listener) }
        }
    override suspend fun authenticate(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password).await()
        auth.pendingAuthResult?.result
    }

    override suspend fun sendRecoveryEmail(email: String) {
        auth.sendPasswordResetEmail(email).await()
    }

    override suspend fun getAccessToken(onSuccess: (String) -> Unit) {
        auth.currentUser?.getIdToken(true)?.addOnCompleteListener {
            if (it.isSuccessful) {
                onSuccess(it.result?.token.orEmpty())
            }
        }
    }

    override suspend fun linkAccount(email: String, password: String): Unit =
        trace(LINK_ACCOUNT_TRACE) {
            val credential = EmailAuthProvider.getCredential(email, password)
            auth.currentUser?.linkWithCredential(credential)?.await()
        }

    override suspend fun createAccount(email: String, password: String, onSucess: () -> Unit, onFailure: () -> Unit) {
        trace(CREATE_ACCOUNT) {
            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                if (it.isSuccessful) {
                    onSucess()
                    Log.d("AuthenticationRepositoryImpl", "createAccount: success")
                } else {
                    onFailure()
                    Log.d("AuthenticationRepositoryImpl", "createAccount: failure")
                }
            }
        }
    }

    override suspend fun deleteAccount() {
        auth.currentUser?.delete()?.await()
    }

    override suspend fun signOut() {
        if (auth.currentUser?.isAnonymous == true) {
            auth.currentUser?.delete()
        }
        auth.signOut()

    }

    companion object {
        private const val LINK_ACCOUNT_TRACE = "linkAccount"
        private const val CREATE_ACCOUNT = "createAccount"
    }

}