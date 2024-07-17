package com.app.springstoreapi.service

import com.app.springstoreapi.model.RoleName
import com.app.springstoreapi.model.User
import com.app.springstoreapi.repository.RoleRepository
import com.app.springstoreapi.repository.UserRepository
import org.springframework.context.annotation.Lazy
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository,
    private val roleRepository: RoleRepository,
    @Lazy private val passwordEncoder: PasswordEncoder
): UserDetailsService {

    // Load user by username
    override fun loadUserByUsername(username: String): UserDetails {
        val user = userRepository.findByUsername(username)
            ?: throw UsernameNotFoundException("User not found with username: $username")

        return org.springframework.security.core.userdetails.User(
            user.username, user.password, user.roles.map { SimpleGrantedAuthority(it.roleName.name) }
        )
    }

    // Register user with role
    fun registerUser(username: String, email: String, password: String): User {
        return registerUserWithRole(username, email, password, RoleName.USER)
    }

    // Register manager with role
    fun registerManager(username: String, email: String, password: String): User {
        return registerUserWithRole(username, email, password, RoleName.MANAGER)
    }

    // Register admin with role
    fun registerAdmin(username: String, email: String, password: String): User {
        return registerUserWithRole(username, email, password, RoleName.ADMIN)
    }

    // Register user with role
    private fun registerUserWithRole(username: String, email: String, password: String, roleName: RoleName): User {
        try {
            if (userRepository.findByUsername(username) != null) {
                throw IllegalArgumentException("Username already exists")
            }
            if (userRepository.findByEmail(email) != null) {
                throw IllegalArgumentException("Email already exists")
            }

            val user = User(
                username = username,
                email = email,
                password = passwordEncoder.encode(password)
            )

            val role = roleRepository.findByRoleName(roleName)
                ?: throw IllegalStateException("Role $roleName not found")

            user.roles = mutableListOf(role)

            return userRepository.save(user)
        } catch (e: Exception) {
            throw RuntimeException("Failed to register user: ${e.message}", e)
        }
    }

    // Login user
    fun findByUsername(username: String): User?{
        return userRepository.findByUsername(username);
    }

}