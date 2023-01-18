package com.jjsh.smartshopping.data.repository

import com.jjsh.smartshopping.data.remote.datasource.RemoteDataSource
import com.jjsh.smartshopping.data.remote.request.toRequest
import com.jjsh.smartshopping.domain.model.Product
import com.jjsh.smartshopping.domain.model.ProductRegistrationInfo
import com.jjsh.smartshopping.domain.repository.ProductRepository
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) : ProductRepository {

    override suspend fun getProducts(
        productId: Long,
        categoryId: Int?,
        direction: String,
        keyword: String?
    ): Result<List<Product>> {
        return remoteDataSource.getProducts(productId, categoryId, direction, keyword)
            .mapCatching { products ->
                products.map { it.toProduct() }
            }
    }

    override suspend fun getProductItem(productId: Long): Result<Product> {
        return remoteDataSource.getProduct(productId).mapCatching { it.toProduct() }
    }

    override suspend fun findProductByBarcode(barcode: Long): Result<Product> {
        return remoteDataSource.findProductByBarcode(barcode).mapCatching { it.toProduct() }
    }

    override suspend fun registerProduct(
        productRegistrationInfo: ProductRegistrationInfo,
        imageIds: List<Long>
    ): Result<Unit> {
        return remoteDataSource.registerProduct(
           productRegistrationInfo.toRequest(imageIds)
        )
    }

    override suspend fun uploadDetailImage(images: List<File>): Result<List<Long>> {
        return remoteDataSource.uploadImages(
            images.map {
                makeImagePart(it)
            }
        )
    }

    private fun makeImagePart(imageFile: File): MultipartBody.Part{
        val mediaType = "multipart/form-data".toMediaTypeOrNull()
        //val mediaType = "image/*".toMediaTypeOrNull()
        val body = imageFile.asRequestBody(mediaType)
        return MultipartBody.Part.createFormData("image",imageFile.name, body)
    }
}