package com.jjsh.smartshopping.domain.usecase

import com.jjsh.smartshopping.domain.repository.AuthRepository
import javax.inject.Inject

class SigninUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(userId: String, password: String) : Result<Unit> {
        return authRepository.signin(userId, password)
    }
}