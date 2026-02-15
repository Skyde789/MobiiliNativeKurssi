package com.example.myapplication

import BottomNavigationBar
import com.example.myapplication.view.TaskScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.example.myapplication.viewmodel.TaskViewModel
import com.example.myapplication.navigation.ROUTE_HOME
import com.example.myapplication.navigation.ROUTE_CALENDAR
import com.example.myapplication.view.CalendarScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MyApplicationTheme {
                val navController = rememberNavController()
                val viewModel: TaskViewModel = viewModel()

                Scaffold(
                    bottomBar = { BottomNavigationBar(navController, onAddClick = { viewModel.setAddTaskDialogActive(true) }) } // bottom navigation here
                ) { innerPadding ->

                    NavHost(
                        navController = navController,
                        startDestination = ROUTE_HOME,
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable(ROUTE_HOME) {
                            TaskScreen(taskModel = viewModel)
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