package com.example.myapplication.ui

import TaskFilter
import TaskSorting
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.model.Task
import com.example.myapplication.viewmodel.TaskViewModel

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


@Composable
fun DetailDialog(
    task: Task,
    onClose: () -> Unit,
    onUpdate: (Task) -> Unit,
    onDelete: (String) -> Unit
) {
    var title by remember { mutableStateOf(task.title) }
    var description by remember { mutableStateOf(task.description) }

    AlertDialog(
        onDismissRequest = onClose,
        title = {
            Text(text = "Edit Task")
        },
        text = {
            Column {
                TextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text(text = "Title") }
                )
                TextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text(text = "Description") }
                )
            }
        },
        confirmButton = {
            Row {
                Button(onClick = { onDelete(task.id); onClose() }) { Text("Delete") }
                Spacer(modifier = Modifier.width(6.dp))
                Button(onClick = { onUpdate(task.copy(title = title, description = description)); onClose() }) { Text("Save") }
            }
        },

        dismissButton = {
            Button(onClick = onClose) {
                Text(text = "Cancel")
            }
        }
    )
}

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
fun FilterChecks(taskModel: TaskViewModel) {
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

@Composable
fun AddTaskForm(taskModel: TaskViewModel){
    val uiState by taskModel.uiState.collectAsState()
    Text(
        text = "Add New Task",
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
        modifier = Modifier.padding(vertical = 8.dp)
    )
    OutlinedTextField(
        value = uiState.newTaskTitle,
        onValueChange = {taskModel.updateNewTaskTitle(it) },
        label = { Text("Title") },
        singleLine = true,
        modifier = Modifier.fillMaxWidth()
    )
    OutlinedTextField(
        value = uiState.newTaskDescription,
        onValueChange = { taskModel.updateNewTaskDescription(it) },
        label = { Text("Description") },
        singleLine = true,
        modifier = Modifier.fillMaxWidth()
    )
    Button(shape = RoundedCornerShape(8.dp), onClick = {
        taskModel.addTask()
    }) {
        Text("Add Task",
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth())
    }
}