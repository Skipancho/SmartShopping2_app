package com.jjsh.smartshopping.data.remote

import com.jjsh.smartshopping.common.Auth
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class RefreshTokenInterceptor @Inject constructor(
    private val auth: Auth
): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val request = original.newBuilder().apply {
            auth.refreshToken?.let{ header("Authorization", it)}
            method(original.method,original.body)
        }.build()

        val response = chain.proceed(request)

        if (response.code/100 == 4){
           auth.signOut()
        }

        return response
    }
}