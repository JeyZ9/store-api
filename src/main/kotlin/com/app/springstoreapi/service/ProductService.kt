package com.app.springstoreapi.service

import com.app.springstoreapi.dto.ProductCategoryDTO
import com.app.springstoreapi.model.Product
import com.app.springstoreapi.repository.ProductRepository
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.FileOutputStream
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Paths

import java.util.*

@Service
class ProductService(private val productRepository: ProductRepository) {

    @Value("\${file.upload-dir}")
    private lateinit var uploadDir: String

//    fun for write file
    @Throws(IOException::class)
    fun saveFile(file: MultipartFile): String {
        val fileName = UUID.randomUUID().toString() + "_" + file.originalFilename
        val filePath = Paths.get(uploadDir, fileName)

        // สร้างโฟลเดอร์หากไม่พบ
        if (!Files.exists(filePath.parent)) {
            Files.createDirectories(filePath.parent)
        }

        // บันทึกไฟล์
        FileOutputStream(filePath.toFile()).use { fos ->
            fos.write(file.bytes)
        }

        return fileName
    }

//    fun for delete file
// ฟังก์ชันสำหรับลบไฟล์
fun deleteFile(fileName: String) {
    if (fileName != "noimg.jpg") {
        val filePath = Paths.get(uploadDir, fileName)
        try {
            Files.deleteIfExists(filePath)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}

//    fun getAllProducts():List<Product> = productRepository.findAll()

    // Get all products with pagination and search
    fun getAllProducts(searchQuery: String?, selectedCategory: Int?, pageable: Pageable): Page<ProductCategoryDTO> {
        return productRepository.findBySearchQueryAndCategory(searchQuery, selectedCategory, pageable)
    }

    // Get product by id with category details
    fun getProductByIdWithCategory(id: Long): Optional<ProductCategoryDTO> = productRepository.findProductWithCategory(id)

    // Get product by id
//    fun getProductById(id: Long): Optional<Product> = productRepository.findById(id) // select * from products where id = ?

    // Create product
    fun createProduct(product: Product, image: MultipartFile?): Product {
        if (image != null) {
            product.productPicture = saveFile(image)
        } else {
            product.productPicture = "noimg.jpg"
        }
        return productRepository.save(product)
    }

    // Update Product
    fun updateProduct(id: Long, updateProduct: Product, image: MultipartFile?): Product {
        return if (productRepository.existsById(id)) {
            val existingProduct = productRepository.findById(id).get()
            existingProduct.productName = updateProduct.productName
            existingProduct.unitPrice = updateProduct.unitPrice
            existingProduct.unitInStock = updateProduct.unitInStock
            existingProduct.categoryId = updateProduct.categoryId
            existingProduct.modifiedDate = updateProduct.modifiedDate
            if (image != null) {
                val newFileName = saveFile(image)
                deleteFile(existingProduct.productPicture!!)
                existingProduct.productPicture = newFileName
            }
            productRepository.save(existingProduct)
        } else {
            throw RuntimeException("Product not found with id: $id")
        }
    }

    // Delete Product
    fun deleteProduct(id: Long) {
        if (productRepository.existsById(id)) {
            val product = productRepository.findById(id).get()
            deleteFile(product.productPicture!!)
            productRepository.delete(product)
        } else {
            throw RuntimeException("Product not found with id: $id")
        }
    }
}