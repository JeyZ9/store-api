package com.app.springstoreapi.repository

import com.app.springstoreapi.dto.ProductCategoryDTO
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
    @Query("""
        SELECT new com.app.springstoreapi.dto.ProductCategoryDTO(p.id, p.productName, p.unitPrice, p.unitInStock, p.productPicture, c.id, c.categoryName, p.createdDate, p.modifiedDate)
        FROM Product p JOIN Category c ON p.categoryId = c.id
        WHERE (:searchQuery IS NULL OR :searchQuery = '' OR LOWER(p.productName) LIKE LOWER(CONCAT('%', :searchQuery, '%')))
          AND (:selectedCategory IS NULL OR p.categoryId = :selectedCategory)
          ORDER BY p.id desc 
    """)
    fun findBySearchQueryAndCategory(
        @Param("searchQuery") searchQuery: String?,
        @Param("selectedCategory") selectedCategory: Int?,
        pageable: Pageable
    ): Page<ProductCategoryDTO>

    @Query("""
        SELECT new com.app.springstoreapi.dto.ProductCategoryDTO(p.id, p.productName, p.unitPrice, p.unitInStock, p.productPicture, c.id, c.categoryName, p.createdDate, p.modifiedDate)
        FROM Product p JOIN Category c ON p.categoryId = c.id
        WHERE p.id = :id
        ORDER BY p.id DESC 
    """)
    fun findProductWithCategory(@Param("id") id: Long): Optional<ProductCategoryDTO>
}