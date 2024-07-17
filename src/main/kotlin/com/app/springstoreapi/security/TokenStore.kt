package com.app.springstoreapi.security

import com.app.springstoreapi.model.InvalidatedTokens
import com.app.springstoreapi.repository.InvalidatedTokenRepository
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class TokenStore(private val invalidatedTokenRepository: InvalidatedTokenRepository){
    fun invalidateToken(token: String, expirationTime: Long){
        val expirationDateTime = LocalDateTime.now().plusSeconds(expirationTime / 1000)
        val invalidatedTokens = InvalidatedTokens(token = token, expirationTime =  expirationDateTime)
        invalidatedTokenRepository.save(invalidatedTokens)
    }

    fun isTokenInvalidated(token: String): Boolean{
        return invalidatedTokenRepository.findByToken(token) != null
    }
}