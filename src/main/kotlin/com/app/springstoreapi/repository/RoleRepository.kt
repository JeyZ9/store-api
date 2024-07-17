package com.app.springstoreapi.repository

import com.app.springstoreapi.model.Role
import com.app.springstoreapi.model.RoleName
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface RoleRepository: JpaRepository<Role, Long> {
    fun findByRoleName(roleName: RoleName): Role?
}