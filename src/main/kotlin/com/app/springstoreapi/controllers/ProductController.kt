package com.app.springstoreapi.controllers

import com.app.springstoreapi.dto.ProductCategoryDTO
import com.app.springstoreapi.model.Product
import com.app.springstoreapi.service.ProductService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.math.BigDecimal
import java.time.LocalDateTime

@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('MANAGER')")
@Tag(name = "Product", description = "This is APIs for products")
@RestController
@RequestMapping("api/v1/products")
class ProductController(private val productService: ProductService) {

    // ฟังก์ชันสำหรับการดึงข้อมูล Product ทั้งหมด
// Get all products with pagination and search
// GET /api/products
//  @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    @Operation(summary = "Get all products" , description = "Get all products from database")
    @GetMapping
    fun getAllProducts(
        @RequestParam(value = "page", defaultValue = "1") page: Int,
        @RequestParam(value = "limit", defaultValue = "100") limit: Int,
        @RequestParam(value = "searchQuery", required = false) searchQuery: String?,
        @RequestParam(value = "selectedCategory", required = false) selectedCategory: Int?
    ): ResponseEntity<Map<String, Any>> {
        val pageable: Pageable = PageRequest.of(page - 1, limit)
        val productsPage: Page<ProductCategoryDTO> = productService.getAllProducts(searchQuery, selectedCategory, pageable)

        val response = mapOf(
            "total" to productsPage.totalElements,
            "products" to productsPage.content
        )

        return ResponseEntity.ok(response)
    }

    // ฟังก์ชันสำหรับการดึงข้อมูล Product ตาม ID
    // GET /api/products/{id}
    @Operation(summary = "Get product by id" , description = "Get product by id from database")
    @GetMapping("/{id}")
    fun getProductById(@PathVariable id: Long): ResponseEntity<ProductCategoryDTO> {
        val product = productService.getProductByIdWithCategory(id)
        return product.map { ResponseEntity.ok(it) }
            .orElseGet { ResponseEntity.notFound().build() }
    }

    // ฟังก์ชันสำหรับการเพิ่มข้อมูล Product
    // POST /api/products
    @Operation(summary = "Create product" , description = "Create product to database")
    @PostMapping(consumes = ["multipart/form-data"])
    fun createProduct(
        @RequestParam productName: String,
        @RequestParam unitPrice: BigDecimal,
        @RequestParam unitInStock: Int,
        @RequestParam(required = false) productPicture: String?,
        @RequestParam categoryId: Int,
        @RequestParam(required = false) createdDate: LocalDateTime?,
        @RequestParam(required = false) modifiedDate: LocalDateTime?,
        @RequestParam(required = false) image: MultipartFile?
    ): ResponseEntity<Product> {
        val product = Product(
            productName = productName,
            unitPrice = unitPrice,
            unitInStock = unitInStock,
            productPicture = productPicture,
            categoryId = categoryId,
            createdDate = createdDate ?: LocalDateTime.now(),
            modifiedDate = modifiedDate
        )
        val createdProduct = productService.createProduct(product, image)
        return ResponseEntity.status(201).body(createdProduct)
    }

    // ฟังก์ชันสำหรับการแก้ไขข้อมูล Product
    // PUT /api/products/{id}
    @Operation(summary = "Update product" , description = "Update product to database")
    @PutMapping("/{id}", consumes = ["multipart/form-data"])
    fun updateProduct(
        @PathVariable id: Long,
        @RequestParam productName: String,
        @RequestParam unitPrice: BigDecimal,
        @RequestParam unitInStock: Int,
        @RequestParam(required = false) productPicture: String?,
        @RequestParam categoryId: Int,
        @RequestParam(required = false) createdDate: LocalDateTime?,
        @RequestParam(required = false) modifiedDate: LocalDateTime?,
        @RequestParam(required = false) image: MultipartFile?
    ): ResponseEntity<Product> {
        val product = Product(
            productName = productName,
            unitPrice = unitPrice,
            unitInStock = unitInStock,
            productPicture = productPicture,
            categoryId = categoryId,
            createdDate = createdDate ?: LocalDateTime.now(),
            modifiedDate = modifiedDate
        )
        val updatedProduct = productService.updateProduct(id, product, image)
        return ResponseEntity.ok(updatedProduct)
    }

    // ฟังก์ชันสำหรับการลบข้อมูล Product
    // DELETE /api/products/{id}
    @Operation(summary = "Delete product" , description = "Delete product from database")
    @DeleteMapping("/{id}")
    fun deleteProduct(@PathVariable id: Long): ResponseEntity<Void> {
        productService.deleteProduct(id)
        return ResponseEntity.noContent().build()
    }
}