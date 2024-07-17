package com.app.springstoreapi.controllers

import com.app.springstoreapi.dto.LoginDTO
import com.app.springstoreapi.dto.RegisterDTO
import com.app.springstoreapi.exception.ResponseModel
import com.app.springstoreapi.service.UserService
import com.app.springstoreapi.utils.JwtUtil
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "Authenticate", description = "Authenticate APIs")
@RestController
@RequestMapping("/api/v1/authenticate")
class UserController(
    private val userService: UserService,
    private val jwtUtil: JwtUtil,
    private val passwordEncoder: PasswordEncoder
) {
    // ฟังก์ชันสำหรับการลงทะเบียน User
    // POST /api/authenticate/register-user
    @Operation(summary = "Register user", description = "Register user to database")
    @PostMapping("/register-user")
    fun registerUser(@RequestBody model: RegisterDTO): ResponseEntity<ResponseModel> {
        return try {
            val user = userService.registerUser(model.username, model.email, model.password)
            ResponseEntity.ok(ResponseModel("Success", "User registered successfully"))
        } catch (e: IllegalStateException) {
            ResponseEntity.badRequest().body(ResponseModel("Error", e.message ?: "Registration failed"))
        } catch (e: IllegalStateException) {
            ResponseEntity.internalServerError().body(ResponseModel("Error", e.message ?: "Registration failed due to system configuration"))
        } catch (e: Exception) {
            ResponseEntity.internalServerError().body(ResponseModel("Error", "User creation failed: ${e.message}"))
        }
    }

    // ฟังก์ชันสำหรับการลงทะเบียน Manager
    // POST /api/authenticate/register-manager
    @Operation(summary = "Register manager", description = "Register manager to database")
    @PostMapping("/register-manager")
    fun registerManager(@RequestBody model: RegisterDTO): ResponseEntity<ResponseModel> {
        return try {
            val user = userService.registerManager(model.username, model.email, model.password)
            ResponseEntity.ok(ResponseModel("Success", "Manager registered successfully"))
        } catch (e: IllegalArgumentException) {
            ResponseEntity.badRequest().body(ResponseModel("Error", e.message ?: "Registration failed"))
        } catch (e: IllegalStateException) {
            ResponseEntity.internalServerError().body(ResponseModel("Error", e.message ?: "Registration failed due to system configuration"))
        } catch (e: Exception) {
            ResponseEntity.internalServerError().body(ResponseModel("Error", "User creation failed: ${e.message}"))
        }
    }

    // ฟังก์ชันสำหรับการลงทะเบียน Admin
    // POST /api/authenticate/register-admin
    @Operation(summary = "Register admin", description = "Register admin to database")
    @PostMapping("/register-admin")
    fun registerAdmin(@RequestBody model: RegisterDTO): ResponseEntity<ResponseModel> {
        return try {
            val user = userService.registerAdmin(model.username, model.email, model.password)
            ResponseEntity.ok(ResponseModel("Success", "Admin registered successfully"))
        } catch (e: IllegalArgumentException) {
            ResponseEntity.badRequest().body(ResponseModel("Error", e.message ?: "Registration failed"))
        } catch (e: IllegalStateException) {
            ResponseEntity.internalServerError().body(ResponseModel("Error", e.message ?: "Registration failed due to system configuration"))
        } catch (e: Exception) {
            ResponseEntity.internalServerError().body(ResponseModel("Error", "User creation failed: ${e.message}"))
        }
    }

    // ฟังก์ชันสำหรับการ login
    @Operation(summary = "Login", description = "Login to system and get JWT token")
    // POST /api/authenticate/login
    @PostMapping("/login")
    fun login(@RequestBody model: LoginDTO): ResponseEntity<ResponseModel> {
        val user = userService.findByUsername(model.username)

        return if (user != null && passwordEncoder.matches(model.password, user.password)) {
            val roles = user.roles.map { it.roleName.name }
            val token = jwtUtil.generateToken(user.username, roles)

            ResponseEntity.ok(ResponseModel(
                "Success",  mapOf(
                    "token" to token,
                    "userName" to user.username,
                    "email" to user.email,
                    "roles" to roles
                ).toString()
            ))
        } else {
            ResponseEntity.status(401).body(ResponseModel("Error", "Invalid username or password"))
        }
    }

    // ฟังก์ชันสำหรับการ logout
    @Operation(summary = "Logout", description = "Logout from the system")
    // POST /api/authenticate/logout
    @PostMapping("/logout")
    fun logout(): ResponseEntity<ResponseModel> {
        val auth: Authentication? = SecurityContextHolder.getContext().authentication
        if (auth != null) {
            SecurityContextHolder.clearContext()
        }
        return ResponseEntity.ok(ResponseModel("Success", "Logged out successfully"))
    }

}