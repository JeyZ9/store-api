package com.app.springstoreapi.repository

import com.app.springstoreapi.model.Product
import org.springframework.data.jpa.repository.JpaRepository

interface ProductRepository:JpaRepository<Product, Long> {}