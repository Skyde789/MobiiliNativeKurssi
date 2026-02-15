package com.example.week5weather.ViewModel

import com.example.week5weather.util.APIResult
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.week5weather.data.model.WeatherResponse
import com.example.week5weather.data.repository.WeatherRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class WeatherViewModel(
    private val repository: WeatherRepository = WeatherRepository()
) : ViewModel() {

    private val _weatherState = MutableStateFlow<APIResult<WeatherResponse>>(APIResult.Loading)
    val weatherState: StateFlow<APIResult<WeatherResponse>> = _weatherState.asStateFlow()

    private val _searchQuery = MutableStateFlow("Oulu")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    init {
        searchWeather()
    }
    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
    }

    fun searchWeather() {
        val city = _searchQuery.value
        if (city.isBlank()) return

        viewModelScope.launch {
            _weatherState.value = APIResult.Loading
            _weatherState.value = repository.getWeather(city)
        }
    }
}