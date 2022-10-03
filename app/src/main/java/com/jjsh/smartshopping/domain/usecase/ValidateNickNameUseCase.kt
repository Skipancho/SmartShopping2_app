package com.jjsh.smartshopping.domain.usecase

import com.jjsh.smartshopping.domain.repository.AuthRepository
import javax.inject.Inject

class ValidateNickNameUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(nickName: String): Result<Unit> {
        return authRepository.validateNickName(nickName)
    }
}