package com.jjsh.smartshopping.domain.usecase

import com.jjsh.smartshopping.domain.model.ProductRegistrationInfo
import com.jjsh.smartshopping.domain.repository.ProductRepository
import java.io.File
import javax.inject.Inject

class RegisterProductUseCase @Inject constructor(
    private val productRepository: ProductRepository
) {
    suspend operator fun invoke(
        productRegistrationInfo: ProductRegistrationInfo,
        images : List<File>
    ) : Result<Unit> {
        return runCatching {
            val imageIds = productRepository.uploadDetailImage(images).getOrThrow()
            productRepository.registerProduct(productRegistrationInfo, imageIds).getOrThrow()
        }
    }
}