package com.app.springstoreapi.repository

import com.app.springstoreapi.model.Product
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

import java.util.Optional

@Repository
interface ProductRepository:JpaRepository<Product, Long> {
    @Query("SELECT p FROM Product p WHERE " +
            "(:searchQuery IS NULL OR p.productName LIKE %:searchQuery%) AND " +
            "(:selectedCategory IS NULL OR p.categoryId = :selectedCategory)")
    fun findBySearchQueryAndCategory(
        @Param("searchQuery") searchQuery: String?,
        @Param("selectedCategory") selectedCategory: Int?,
        pageable: Pageable
    ): Page<Product>

    @Query("SELECT p FROM Product p JOIN Category c ON p.categoryId = c.id WHERE p.id = :id")
    fun findProductWithCategory(@Param("id") id: Long): Optional<Map<String, Any>>
}