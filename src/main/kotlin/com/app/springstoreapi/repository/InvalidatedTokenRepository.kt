package com.app.springstoreapi.repository

import com.app.springstoreapi.model.InvalidatedTokens
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
interface InvalidatedTokenRepository: JpaRepository<InvalidatedTokens, Long> {
    fun findByToken(token: String): InvalidatedTokens?
    fun deleteByExpirationTimeBefore(now: LocalDateTime)
}