package com.boo.sample.data.api

data class Response<T>(
    val response: T? = null,
    val resultMessage: String? = null,
    val resultCode: String? = null
)
