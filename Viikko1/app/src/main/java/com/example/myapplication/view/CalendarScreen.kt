package com.example.myapplication.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.myapplication.data.model.Task
import com.example.myapplication.viewmodel.TaskViewModel
import java.time.Instant
import java.time.ZoneId

@Composable
fun CalendarScreen(taskModel: TaskViewModel) {
    val uiState by taskModel.uiState.collectAsState()
    val grouped = uiState.tasks.groupBy { task ->
        val instant = Instant.ofEpochMilli(task.createdAt)
        val localDate = instant.atZone(ZoneId.systemDefault()).toLocalDate()
        localDate
    }
    val isAddDialogOpen by taskModel.isAddDialogOpen.collectAsState()

    LazyColumn(modifier = Modifier.padding(16.dp)) {
        grouped.forEach { (date, tasksOfDay) ->
            item {
                Text(
                    text = date.toString(),
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }
            items(tasksOfDay) { task ->
                CalendarTaskCard(task = task, onTaskClick = { taskModel.selectTask(task) })
            }
        }
    }


    if(uiState.selectedTask != null)
    {
        uiState.selectedTask?.let { task ->
            DetailDialog(
                task = task,
                onClose = { taskModel.deselectTask() },
                onUpdate = { updatedTask -> taskModel.updateTask(
                    title = updatedTask.title,
                    description = updatedTask.description
                ) },
                onDelete = { taskModel.deleteTask(task) }
            )
        }
    }
    else if(isAddDialogOpen)
    {
        AddDialog(
            onConfirm = { title, description ->
                taskModel.addTask(title, description)
                taskModel.closeAddDialog()
            },
            onClose = { taskModel.closeAddDialog() }
        )
    }
}
@Composable
fun CalendarTaskCard(
    task: Task,
    onTaskClick: (Int) -> Unit
) {
    Card(
        modifier = Modifier
            .padding(vertical = 4.dp)
            .fillMaxWidth()
            .clickable { onTaskClick(task.id) }
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(task.title, style = MaterialTheme.typography.titleMedium)
            if (task.description.isNotBlank()) {
                Text(task.description, style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}