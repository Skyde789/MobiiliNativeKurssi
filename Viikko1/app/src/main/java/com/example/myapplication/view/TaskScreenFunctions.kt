package com.example.myapplication.view

import TaskFilter
import TaskSorting
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.model.Task
import com.example.myapplication.viewmodel.TaskViewModel



@Composable
fun TaskDropdownMenu(
    currentFilter: TaskFilter,
    currentSorting: TaskSorting,
    setFilter: (TaskFilter) -> Unit,
    setSorting: (TaskSorting) -> Unit
) {
    var filterExpanded by remember { mutableStateOf(false) }
    var sortingExpanded by remember { mutableStateOf(false) }

    Column(modifier = Modifier.padding(8.dp)) {
        Box(modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)) {
            Button(
                onClick = { filterExpanded = true },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("Filter: ${currentFilter.name}")
            }

            DropdownMenu(
                expanded = filterExpanded,
                onDismissRequest = { filterExpanded = false },
                modifier = Modifier.fillMaxWidth()
            ) {
                TaskFilter.values().forEach { filter ->
                    DropdownMenuItem(
                        text = { Text(filter.name) },
                        onClick = {
                            setFilter(filter)
                            filterExpanded = false
                        }
                    )
                }
            }
        }

        Box(modifier = Modifier.fillMaxWidth()) {
            Button(
                onClick = { sortingExpanded = true },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("Sort: ${currentSorting.name}")
            }

            DropdownMenu(
                expanded = sortingExpanded,
                onDismissRequest = { sortingExpanded = false },
                modifier = Modifier.fillMaxWidth()
            ) {
                TaskSorting.values().forEach { sort ->
                    DropdownMenuItem(
                        text = { Text(sort.name) },
                        onClick = {
                            setSorting(sort)
                            sortingExpanded = false
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun TaskFilterDropdowns(taskModel: TaskViewModel) {
    val uiState by taskModel.uiState.collectAsState()

    Column() {
        Text(
            text = "Filters",
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        TaskDropdownMenu(
            currentFilter = uiState.filter,
            currentSorting = uiState.sort,
            setFilter = { taskModel.setFilter(it) },
            setSorting = { taskModel.setSorting(it) }
        )
    }
}