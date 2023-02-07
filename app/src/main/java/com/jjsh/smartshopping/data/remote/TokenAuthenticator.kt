package com.jjsh.smartshopping.data.remote

import android.content.Context
import com.jjsh.smartshopping.data.auth.Auth
import com.jjsh.smartshopping.data.remote.api.TokenService
import com.jjsh.smartshopping.presentation.extension.clearTaskAndStart
import com.jjsh.smartshopping.presentation.ui.signin.SigninActivity
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject

class TokenAuthenticator @Inject constructor(
    private val auth: Auth,
    private val service: TokenService,
    @ApplicationContext private val context: Context
) : Authenticator {
    override fun authenticate(route: Route?, response: Response): Request? {
        if (response.code == 401) {
            return runBlocking {
                val tokenResponse = refreshToken()

                handleTokenResponse(tokenResponse)

                auth.token?.let { token ->
                    response.request
                        .newBuilder()
                        .header("Authorization", token)
                        .build()
                }
            }
        }
        return null
    }

    private suspend fun refreshToken() =
        withContext(Dispatchers.IO) {
            try {
                service.refreshToken()
            } catch (e: Exception) {
                ApiResponse.error("인증 실패")
            }
        }

    private fun handleTokenResponse(tokenResponse: ApiResponse<String>) {
        if (tokenResponse.success && tokenResponse.data != null) {
            auth.token = tokenResponse.data
        } else {
            auth.signOut()
            context.clearTaskAndStart<SigninActivity>()
        }
    }
}