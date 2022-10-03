package com.jjsh.smartshopping.domain.usecase

import com.jjsh.smartshopping.domain.repository.AuthRepository
import javax.inject.Inject

class SignupUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(
        userId: String,
        password: String,
        nickName: String,
        name: String
    ): Result<Unit> {
        return authRepository.signup(userId, password, nickName, name)
    }
}