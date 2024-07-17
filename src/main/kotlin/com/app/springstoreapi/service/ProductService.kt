package com.app.springstoreapi.service

import com.app.springstoreapi.model.Product
import com.app.springstoreapi.repository.ProductRepository
import org.springframework.stereotype.Service

import java.util.*

@Service
class ProductService(private val productRepository: ProductRepository) {
    fun getAllProducts():List<Product> = productRepository.findAll()

    // Get product by id
    fun getProductById(id: Long): Optional<Product> = productRepository.findById(id) // select * from products where id = ?

    // Create product
    fun createProduct(product: Product): Product = productRepository.save(product)

    // Update Product
    fun updateProduct(id: Long, updateProduct: Product): Product {
        return if (productRepository.existsById(id)) {
            updateProduct.id = id
            productRepository.save(updateProduct)
        } else {
            throw RuntimeException("Product not found with id: $id")
        }
    }

    // Delete Product
    fun deleteProduct(id: Long) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id)
        } else {
            throw RuntimeException("Product not found with id: $id")
        }
    }
}