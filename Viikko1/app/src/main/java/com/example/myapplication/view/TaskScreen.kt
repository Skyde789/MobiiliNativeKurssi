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
import com.example.myapplication.model.Task
import com.example.myapplication.viewmodel.TaskViewModel

@Composable
fun TaskScreen(taskModel: TaskViewModel)
{
    val uiState by taskModel.uiState.collectAsState()

    val filteredTasks = taskModel.getFilteredTasks()

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

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(horizontal = 16.dp)){

        TaskFilterDropdowns(taskModel)
        Text(
            text = "Tasks",
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            modifier = Modifier.padding(vertical = 8.dp)
        )
        LazyColumn(
            modifier = Modifier
            .fillMaxWidth()
            .weight(1f))
        {
            items(filteredTasks) { item ->
                TaskElement(item, taskModel)
            }
        }
    }
}

@Composable
fun TaskElement(task: Task, taskModel: TaskViewModel){
    Card(modifier = Modifier
        .padding(8.dp)
        .clickable {taskModel.selectTask(task.id)}){
        Row( verticalAlignment = Alignment.CenterVertically){
            Text(
                text = task.title,
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 16.dp)
            )
            Checkbox(task.done, onCheckedChange = {
                taskModel.toggleDone(task.id)
            })
        }
    }
}