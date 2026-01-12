package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication.domain.Task
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.example.myapplication.domain.mockTasks
import com.example.myapplication.domain.toggleDone
import com.example.myapplication.domain.addTask
import com.example.myapplication.domain.filterByDone
import com.example.myapplication.domain.sortByDueDate
import java.time.LocalDate

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) {
                    HomeScreen()
                }
            }
        }
    }
}

@Composable
fun HomeScreen() {
    var taskList: List<Task> by remember { mutableStateOf(mockTasks) }
    var text by remember { mutableStateOf("") }
    var text2 by remember { mutableStateOf("") }
    var check1: Boolean by remember {mutableStateOf(false)}
    var check2: Boolean by remember {mutableStateOf(false)}

    Column(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
            .padding(5.dp)
    ) {
        Row(modifier = Modifier.padding(vertical = 5.dp)) {
            Text("Filter by done:")
            Checkbox(modifier = Modifier.offset(y = -10.dp),checked = check1, onCheckedChange = {
                check1 = !check1
            })
        }
        Row(modifier = Modifier.padding(vertical = 5.dp)) {
            Text("Sort by date:")
            Checkbox(modifier = Modifier.offset(y = -10.dp),checked = check2, onCheckedChange = {
                check2 = !check2
            })
        }

        var displayedTasks = taskList
        if (check1) displayedTasks = filterByDone(displayedTasks, true)
        if (check2) displayedTasks = sortByDueDate(displayedTasks)

        Column(){
            displayedTasks.forEach { task ->
                Row() {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(task.title + ": " + task.description)
                        Text("Due: ${task.dueDate}")
                    }
                    Checkbox(
                        checked = task.done,
                        onCheckedChange = { taskList = toggleDone(taskList, task.id) }
                    )
                }
            }
        }

        Text(
            modifier = Modifier.padding(vertical = 10.dp),
            text = "Add new task:"
        )
        OutlinedTextField(
            value = text,
            onValueChange = { text = it },
            label = { Text("Title") },
            singleLine = true,
        )
        OutlinedTextField(
            value = text2,
            onValueChange = { text2 = it },
            label = { Text("Description") },
            singleLine = true,
        )

        Button(onClick = {
            val newTask = Task(
                id = taskList.size,
                title = text,
                description = text2,
                priority = 1,
                dueDate = LocalDate.now(),
                done = false
            )
            taskList = addTask(taskList, newTask)
        }) {
            Text("Add Task")
        }
    }

}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyApplicationTheme {
        HomeScreen()
    }
}