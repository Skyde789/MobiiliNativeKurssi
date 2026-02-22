package com.example.myapplication.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.myapplication.data.model.Task

@Composable
fun DetailDialog(
    task: Task,
    onClose: () -> Unit,
    onUpdate: (Task) -> Unit,
    onDelete: (Int) -> Unit
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
                    maxLines = 3,
                    label = { Text(text = "Description") }
                )
            }
        },
        confirmButton = {
            Row {
                Button(onClick = { onDelete(task.id); onClose() }) { Text("Delete") }
                Spacer(modifier = Modifier.width(6.dp))
                Button(onClick = {
                    onUpdate(task.copy(title = title, description = description))
                    onClose()
                }) {
                    Text("Save")
                }
            }
        },

        dismissButton = {
            Button(onClick = onClose) {
                Text(text = "Cancel")
            }
        }
    )
}
