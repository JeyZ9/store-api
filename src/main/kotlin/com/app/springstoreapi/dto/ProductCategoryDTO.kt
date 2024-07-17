package com.app.springstoreapi.dto

import java.math.BigDecimal
import java.time.LocalDateTime

data class ProductCategoryDTO(
    val id: Long,
    val productName: String,
    val unitPrice: BigDecimal,
    val unitStock: Int?,
    val productPicture: String?,
    val categoryId: Long,
    val categoryName: String?,
    val createDate: LocalDateTime,
    val modifiedDate: LocalDateTime?
)
