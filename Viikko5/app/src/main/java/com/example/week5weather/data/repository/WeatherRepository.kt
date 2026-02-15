package com.example.week5weather.data.repository

import android.net.http.HttpException
import com.example.week5weather.BuildConfig
import com.example.week5weather.data.model.WeatherResponse
import com.example.week5weather.data.remote.RetrofitClient
import com.example.week5weather.util.APIResult
import java.io.IOException

class WeatherRepository {

    private val apiService = RetrofitClient.weatherApiService
    private val apiKey = BuildConfig.OPENWEATHER_API_KEY

    suspend fun getWeather(city: String): APIResult<WeatherResponse> {
        return try {
            val response = apiService.getWeather(city, apiKey)
            APIResult.Success(response)
        } catch (e: IOException) {
            APIResult.Error(Exception("Connection Error: ${e.message}"))
        } catch (e: HttpException) {
            APIResult.Error(Exception("Server Error: ${e.message}"))
        } catch (e: Exception) {
            APIResult.Error(Exception("Unknown Error: ${e.message}"))
        }
    }
}
