package com.example.week5weather.util


sealed class APIResult<out T> {
    data class Success<T>(val data: T) : APIResult<T>()
    data class Error(val exception: Exception) : APIResult<Nothing>()
    object Loading : APIResult<Nothing>()
}