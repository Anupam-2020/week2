package com.example.industrialstructurecasestudy.rest

suspend fun <T: Any> handleRequest(requestFunc: suspend () -> T): Result<T> {
    return try {
        Result.success(requestFunc.invoke())
    } catch (he: Exception) {
        Result.failure(he)
    }
}