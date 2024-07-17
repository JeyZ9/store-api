package com.app.springstoreapi.model

import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

@Entity
@Table(name = "products")
data class Product (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,

    @Column(name = "product_name")
    @Size(min = 3, max = 50)
    @NotBlank(message = "Product name is required")
    val productName: String ="",

    @Column(name = "product_price")
    @Size(min = 1, max = 10)
    @NotBlank(message = "Product price is required")
    val productPrice: Double = 0.0,

    @Column(name = "product_quantity")
    @Size(min = 1, max = 10)
    @NotBlank(message = "Product quantity is required")
    val productQuantity: Int = 0
)
