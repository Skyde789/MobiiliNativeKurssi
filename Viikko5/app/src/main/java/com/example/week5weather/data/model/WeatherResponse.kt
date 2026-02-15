package com.example.week5weather.data.model

data class WeatherResponse(
    val weather: List<Weather>,
    val main: Main,
    val wind: Wind,
    val name: String,
    val sys: Sys
)

data class Weather(
    val main: String,
    val description: String,
    val icon: String
)

data class Main(
    val temp: Double,
    val feels_like: Double,
    val temp_min: Float,
    val temp_max: Float,
    val pressure: Int,
    val humidity: Int
)

data class Wind(
    val speed: Double,
    val deg: Int
)

data class Sys(
    val country: String,
    val sunrise: Long,
    val sunset: Long
)
