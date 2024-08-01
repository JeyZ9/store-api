package com.app.springstoreapi.config

import com.app.springstoreapi.filter.JwtRequestFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Lazy
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@Configuration
@EnableWebSecurity
//กำหนดสิทธิ์ role config
@EnableMethodSecurity(prePostEnabled = true, securedEnabled = true)
class SecurityConfig(
    private val userDetailsService: UserDetailsService,
    @Lazy private val jwtRequestFilter: JwtRequestFilter
) {
    @Bean
    fun securityFilterChain(http:HttpSecurity): SecurityFilterChain {
        http
//            ไม่ต้องเช็คเรื่องการโจมตี
            .csrf({ it.disable() })
//            ไม่ต้องเช็คเรื่อง cors
//            .cors({ it.disable() })
            // check cors
            .cors { it.configurationSource(corsConfigurationSource()) }
            .authorizeHttpRequests{ auth ->
                auth
                    .requestMatchers("/","index.html","/v3/api-docs/**","/swagger-ui/**", "/swagger-ui.html","/css/**", "/images/**", "/upload/**").permitAll()
                    .requestMatchers("/api/v1/authenticate/**").permitAll()
                    .anyRequest().authenticated()
            }
            .sessionManagement { session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            }
            .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter::class.java)
        return http.build()
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    fun authenticationManager(authenticationConfiguration: AuthenticationConfiguration): AuthenticationManager{
        return authenticationConfiguration.authenticationManager
    }

    @Bean
    fun corsConfigurationSource(): UrlBasedCorsConfigurationSource {
        val source = UrlBasedCorsConfigurationSource()
        val config = CorsConfiguration()
        config.allowCredentials = true

        // Allow specific origins
        config.addAllowedOrigin("http://localhost:8080")
        config.addAllowedOrigin("http://localhost:4200")
        config.addAllowedOrigin("http://localhost:3000")
        config.addAllowedOrigin("http://localhost:5173")
        config.addAllowedOrigin("http://localhost:5000")
        config.addAllowedOrigin("http://localhost:5001")
        config.addAllowedOriginPattern("https://*.itgenius.co.th")
        config.addAllowedOriginPattern("https://*.azurewebsites.net")
        config.addAllowedOriginPattern("https://*.netlify.app")
        config.addAllowedOriginPattern("https://*.vercel.app")
        config.addAllowedOriginPattern("https://*.herokuapp.com")
        config.addAllowedOriginPattern("https://*.firebaseapp.com")
        config.addAllowedOriginPattern("https://*.github.io")
        config.addAllowedOriginPattern("https://*.gitlab.io")
        config.addAllowedOriginPattern("https://*.onrender.com")
        config.addAllowedOriginPattern("https://*.surge.sh")
        config.addAllowedHeader("*")
        config.addAllowedMethod("*")

        source.registerCorsConfiguration("/**", config)
        return source
    }
}