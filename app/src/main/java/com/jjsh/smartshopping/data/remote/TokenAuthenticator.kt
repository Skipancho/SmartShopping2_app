package com.jjsh.smartshopping.data.remote

import com.jjsh.smartshopping.common.Auth
import com.jjsh.smartshopping.data.remote.api.TokenService
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
    private val service: TokenService
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
        }
    }
}