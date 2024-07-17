package com.app.springstoreapi.model

import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

@Entity
@Table(name = "categories")
data class Category(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // กำหนดให้ field เป็น Primary Key และ Auto Increment
    var id: Int = 0,
    @Column(name = "categoryname")
    @Size(min = 3, max = 50)
    @NotBlank(message = "Category name is required")
    var categoryName: String = "",
    @Column(name = "categorystatus")
    var categoryStatus: Int = 0
)

