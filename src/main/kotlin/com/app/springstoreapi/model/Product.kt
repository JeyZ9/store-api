package com.app.springstoreapi.model

import com.fasterxml.jackson.annotation.JsonFormat
import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity
@Table(name = "products")
data class Product (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // กำหนดให้ field เป็น Primary Key และ Auto Increment
    var id: Long = 0,

    @Column(name = "productname")
    @Size(min = 3, max = 50)
    @NotBlank(message = "Product name is required")
    var productName: String? = null,

    @Column(name = "unitprice", precision = 10, scale = 2)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "0.00")
    @NotBlank(message = "Unit price is required")
    var unitPrice: BigDecimal? = null,

    @Column(name = "unitinstock")
    @NotBlank(message = "Unit in stock is required")
    var unitInStock: Int? = null,

    @Column(name = "productpicture")
    var productPicture: String? = null,

    @Column(name = "categoryid")
    @NotBlank(message = "Category id is required")
    var categoryId: Int = 0,

    @Column(name = "createddate")
    var createdDate: LocalDateTime = LocalDateTime.now(),

    @Column(name = "modifieddate")
    var modifiedDate: LocalDateTime? = null
)
