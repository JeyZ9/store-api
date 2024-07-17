package com.app.springstoreapi.controllers

import com.app.springstoreapi.model.Product
import com.app.springstoreapi.service.ProductService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@Tag(name = "Product", description = "This is APIs for products")
@RestController
@RequestMapping("api/v1/products")
class ProductController(private val productService: ProductService) {

    @Operation(summary = "Get all products", description = "Get all products from database")
    @GetMapping
    fun getAllProducts() = productService.getAllProducts()

    @Operation(summary = "Get product By Id", description = "Get product By Id from database")
    @GetMapping("/{id}")
    fun getProductById(@PathVariable id: Long): ResponseEntity<Product> {
        val product = productService.getProductById(id)
        return product.map { ResponseEntity.ok(it) }
            .orElseGet{ ResponseEntity(HttpStatus.NOT_FOUND) }
    }

    @Operation(summary = "Create product", description = "Create new product to database")
    @PostMapping
    fun createProduct(@RequestBody product: Product): ResponseEntity<Product> {
        val newProduct = productService.createProduct(product)
        return ResponseEntity(newProduct, HttpStatus.CREATED)
    }

    // Update Product
    @Operation(summary = "Update product By id", description = "Update product By Id from database")
    @PutMapping("/{id}")
    fun updateProduct(@PathVariable id: Long, @RequestBody product: Product): ResponseEntity<Product> {
        val updatedProduct = productService.updateProduct(id, product)
        return ResponseEntity.ok(updatedProduct)
    }

    // Delete Product
    @Operation(summary = "Delete product By Id", description = "Delete product by id from database")
    @DeleteMapping("/{id}")
    fun deleteProduct(@PathVariable id: Long): ResponseEntity<Void> {
        productService.deleteProduct(id)
        return ResponseEntity(HttpStatus.NO_CONTENT)
    }
}