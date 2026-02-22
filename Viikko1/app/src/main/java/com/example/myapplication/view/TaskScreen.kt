package com.example.myapplication.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.data.model.Task
import com.example.myapplication.viewmodel.TaskViewModel

@Composable
fun TaskScreen(taskModel: TaskViewModel)
{
    val uiState by taskModel.uiState.collectAsState()
    val isAddDialogOpen by taskModel.isAddDialogOpen.collectAsState()

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

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(horizontal = 16.dp)){

        TaskFilterDropdowns(taskModel)
        Text(
            text = "Tasks - " + uiState.pendingCount + " tasks pending",
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            modifier = Modifier.padding(vertical = 8.dp)
        )
        LazyColumn(
            modifier = Modifier
            .fillMaxWidth()
            .weight(1f))
        {
            items(uiState.tasks) { item ->
                TaskElement(item, taskModel)
            }
        }
    }
}

@Composable
fun TaskElement(task: Task, taskModel: TaskViewModel){
    Card(modifier = Modifier
        .padding(8.dp)
        .clickable {taskModel.selectTask(task)}){
        Row( verticalAlignment = Alignment.CenterVertically){
            Text(
                text = task.title,
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 16.dp)
            )
            Checkbox(task.isCompleted, onCheckedChange = {
                taskModel.toggleDone(task)
            })
        }
    }
}