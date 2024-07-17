package com.app.springstoreapi.seeders

import com.app.springstoreapi.model.Role
import com.app.springstoreapi.model.RoleName
import com.app.springstoreapi.repository.RoleRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component

@Component
class DatabaseSeeder(private val roleRepository: RoleRepository): CommandLineRunner {

    override  fun run (vararg args: String?){
//        check role from database
        if (roleRepository.count() == 0L){
//            create new role
            val roles = listOf(
                Role(roleName = RoleName.USER),
                Role(roleName = RoleName.MANAGER),
                Role(roleName = RoleName.ADMIN),
            )
//            save role in to database
            roleRepository.saveAll(roles)
        }
    }

}