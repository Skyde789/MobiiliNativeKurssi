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
import com.example.myapplication.model.Task
import com.example.myapplication.viewmodel.TaskViewModel

@Composable
fun CalendarScreen(taskModel: TaskViewModel) {
    val uiState by taskModel.uiState.collectAsState()
    val grouped = taskModel.getFilteredTasks().groupBy { it.date }

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
                CalendarTaskCard(task = task, onTaskClick = { taskModel.selectTask(task.id) })
            }
        }
    }

    if(uiState.selectedTask != null)
    {
        uiState.selectedTask?.let { task ->
            DetailDialog(
                task = task,
                onClose = { taskModel.deselectTask() },
                onUpdate = { updatedTask -> taskModel.updateTask(updatedTask) },
                onDelete = { id -> taskModel.removeTask(id) }
            )
        }
    }
    else if(uiState.addNewTask)
    {
        AddDialog(
            title = uiState.newTaskTitle,
            description = uiState.newTaskDescription,
            onTitleChange = { taskModel.updateNewTaskTitle(it) },
            onDescriptionChange = { taskModel.updateNewTaskDescription(it) },
            onConfirm = { taskModel.addTask() },
            onClose = { taskModel.setAddTaskDialogActive(false) }
        )
    }
}
@Composable
fun CalendarTaskCard(
    task: Task,
    onTaskClick: (String) -> Unit
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