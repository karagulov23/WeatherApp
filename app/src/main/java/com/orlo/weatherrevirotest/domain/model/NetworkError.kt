package com.orlo.weatherrevirotest.domain.model

data class NetworkError(
    val error: ApiError,
    val throwable: Throwable? = null
)

enum class ApiError(val message: String){
    NetworkError("Network error"),
    UnknownResponse("Unknown response"),
    UnknownError("Unknown Error")
}