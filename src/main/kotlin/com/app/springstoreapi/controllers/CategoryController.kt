package com.app.springstoreapi.controllers

import com.app.springstoreapi.model.Category
import com.app.springstoreapi.service.CategoryService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

//Only role
@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('MANAGER') or hasAuthority('USER')")
@Tag(name = "Category", description="APIs for managing categories")
@RestController
@RequestMapping("/api/v1/category")
class CategoryController (private val categoryService: CategoryService) {
    // ฟังก์ชันสำหรับการดึงข้อมูล Category ทั้งหมด
    // GET /api/categories
    @Operation(summary = "Get All Categories", description = "Get all categories from database")
    @GetMapping
    fun getCategories(): ResponseEntity<List<Category>> {
        val categories = categoryService.getAllCategories()
        return ResponseEntity.ok(categories)
    }

    // ฟังก์ชันสำหรับการดึงข้อมูล Category ตาม ID
    // GET /api/categories/{id}
    @Operation(summary = "Get categories by Id", description = "Get categories by id from database")
    @GetMapping("/{id}")
    fun getCategory(@PathVariable id: Long): ResponseEntity<Category> {
        val category = categoryService.getCategoryById(id)
        return if (category.isPresent) {
            ResponseEntity.ok(category.get())
        } else {
            ResponseEntity.notFound().build()
        }
    }

    // ฟังก์ชันสำหรับการเพิ่มข้อมูล Category
    // POST /api/categories
//    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('MANAGER')")
    @Operation(summary = "Create categories", description = "Create new categories to database")
    @PostMapping
    fun addCategory(@RequestBody category: Category): ResponseEntity<Category> {
        val savedCategory = categoryService.addCategory(category)
        return ResponseEntity.ok(savedCategory)
    }

    // ฟังก์ชันสำหรับการแก้ไขข้อมูล Category
    // PUT /api/categories/{id}
    @Operation(summary = "Update category By Id", description = "Update category by category id from database")
    @PutMapping("/{id}")
    fun updateCategory(@PathVariable id: Long, @RequestBody category: Category): ResponseEntity<Category> {
        val updatedCategory = categoryService.updateCategory(id, category)
        return if (updatedCategory.isPresent) {
            ResponseEntity.ok(updatedCategory.get())
        } else {
            ResponseEntity.notFound().build()
        }
    }

    // ฟังก์ชันสำหรับการลบข้อมูล Category
    // DELETE /api/categories/{id}
    @Operation(summary = "Delete categories By Id", description = "Delete categories by category id from database")
    @DeleteMapping("/{id}")
    fun deleteCategory(@PathVariable id: Long): ResponseEntity<Category> {
        val deletedCategory = categoryService.deleteCategory(id)
        return if (deletedCategory.isPresent) {
            ResponseEntity.ok(deletedCategory.get())
        } else {
            ResponseEntity.notFound().build()
        }
    }

}