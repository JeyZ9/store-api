package com.app.springstoreapi.model

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "invalidated_tokens")
data class InvalidatedTokens(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false)
    val token: String,

    @Column(name = "expiration_time", nullable = false)
    val expirationTime: LocalDateTime
)
