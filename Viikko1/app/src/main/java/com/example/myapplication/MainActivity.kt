package com.example.myapplication

import BottomNavigationBar
import com.example.myapplication.view.TaskScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.example.myapplication.viewmodel.TaskViewModel
import com.example.myapplication.navigation.ROUTE_HOME
import com.example.myapplication.navigation.ROUTE_CALENDAR
import com.example.myapplication.view.CalendarScreen
import com.example.myapplication.data.local.AppDatabase
import com.example.myapplication.data.repository.TaskRepository
import androidx.lifecycle.ViewModelProvider
class MainActivity : ComponentActivity() {

    private val database by lazy { AppDatabase.getDatabase(applicationContext) }
    private val repository by lazy { TaskRepository(database.taskDao()) }

    private val viewModel: TaskViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return TaskViewModel(repository) as T
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MyApplicationTheme {
                val navController = rememberNavController()

                Scaffold(
                    bottomBar = { BottomNavigationBar(navController, onAddClick = { viewModel.openAddDialog() }) }
                ) { innerPadding ->

                    NavHost(
                        navController = navController,
                        startDestination = ROUTE_HOME,
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable(ROUTE_HOME) {
                            TaskScreen(taskModel = viewModel) // käytä Activityn viewModelia
                        }

                        composable(ROUTE_CALENDAR) {
                            CalendarScreen(taskModel = viewModel)
                        }
                    }
                }
            }
        }
    }
}