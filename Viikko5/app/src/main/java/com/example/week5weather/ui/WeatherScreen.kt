package com.example.week5weather.ui
import androidx.compose.foundation.layout.Box
import com.example.week5weather.util.APIResult
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.week5weather.ViewModel.WeatherViewModel
import com.example.week5weather.ui.components.ErrorScreen
import com.example.week5weather.ui.components.SearchBar
import com.example.week5weather.ui.components.WeatherContent

@Composable
fun WeatherScreen(viewModel: WeatherViewModel = viewModel()) {
    val searchQuery by viewModel.searchQuery.collectAsState()
    val weatherState by viewModel.weatherState.collectAsState()

    Column(modifier = Modifier.fillMaxSize()) {
        SearchBar(
            query = searchQuery,
            onQueryChange = { viewModel.onSearchQueryChange(it) },
            onSearch = { viewModel.searchWeather() }
        )

        when (val state = weatherState) {
            is APIResult.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            is APIResult.Success -> {
                WeatherContent(weather = state.data)
            }
            is APIResult.Error -> {
                ErrorScreen(
                    message = state.exception.message ?: "Error",
                    onRetry = { viewModel.searchWeather() }
                )
            }
        }
    }
}

