package com.example.myapplication.view

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@Composable
fun AddDialog(
    onConfirm: (String, String) -> Unit,
    onClose: () -> Unit
) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onClose,
        title = {
            Text(text = "Add Task")
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
            Button(onClick = {
                onConfirm(title, description)
                title = ""
                description = ""
            }) {
                Text("Add")
            }
        },
        dismissButton = {
            Button(onClick = {
                onClose()
                title = ""
                description = ""
            }) {
                Text("Cancel")
            }
        }
    )
}
