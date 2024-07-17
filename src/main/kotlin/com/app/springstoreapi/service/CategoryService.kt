package com.app.springstoreapi.service

import com.app.springstoreapi.model.Category
import com.app.springstoreapi.repository.CategoryRepository
import org.springframework.stereotype.Service

import java.util.*

@Service
class CategoryService(private val categoryRepository: CategoryRepository) {
    // Get all categories
    fun getAllCategories(): List<Category> {
        return categoryRepository.findAll()
    }

    // Get category by id
    fun getCategoryById(id: Long): Optional<Category> {
        return categoryRepository.findById(id)
    }

    // Create category
    fun addCategory(category: Category): Category {
        return categoryRepository.save(category)
    }

    // Update category
    fun updateCategory(id: Long, category: Category): Optional<Category> {
        val existingCategory = categoryRepository.findById(id)
        return if (existingCategory.isPresent) {
            val cat = existingCategory.get()
            cat.categoryName = category.categoryName
            cat.categoryStatus = category.categoryStatus
            Optional.of(categoryRepository.save(cat))
        } else {
            Optional.empty()
        }
    }

    // Delete category
    fun deleteCategory(id: Long): Optional<Category> {
        val existingCategory = categoryRepository.findById(id)
        return if (existingCategory.isPresent) {
            categoryRepository.delete(existingCategory.get())
            existingCategory
        } else {
            Optional.empty()
        }
    }
}