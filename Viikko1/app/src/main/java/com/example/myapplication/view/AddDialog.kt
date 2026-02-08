package com.example.myapplication.view

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable

@Composable
fun AddDialog(
    title: String,
    description: String,
    onTitleChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
    onConfirm: () -> Unit,
    onClose: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onClose,
        title = {
            Text(text = "Add Task")
        },
        text = {
            Column {
                TextField(
                    value = title,
                    onValueChange = onTitleChange,
                    label = { Text(text = "Title") }
                )
                TextField(
                    value = description,
                    onValueChange = onDescriptionChange,
                    label = { Text(text = "Description") }
                )
            }
        },
        confirmButton = {
            Button(onClick = onConfirm) {
                Text("Add")
            }
        },
        dismissButton = {
            Button(onClick = onClose) {
                Text("Cancel")
            }
        }
    )
}
