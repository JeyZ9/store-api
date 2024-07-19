package com.app.springstoreapi.exception

data class ResponseModel(
    val status: String,
    val message: String,
    val data: Any? = null
)