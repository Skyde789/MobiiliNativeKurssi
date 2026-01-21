import ads_mobile_sdk.h6
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.domain.Task
import com.example.myapplication.viewmodel.TaskViewModel
import java.time.LocalDate

@Composable
fun HomeScreen(modifier: Modifier, taskModel: TaskViewModel) {

    val tasks by taskModel.taskList.collectAsState()
    Column(modifier = modifier
        .fillMaxSize()
        .padding(horizontal = 16.dp)){
        filterChecks(taskModel)
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
            items(tasks) { item ->
                Row( verticalAlignment = Alignment.CenterVertically){
                    Text(
                        text = item.title,
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 16.dp)
                    )
                    Checkbox(item.done, onCheckedChange = {
                        taskModel.toggleDone(item.id)
                    })
                    Button(
                        onClick = { taskModel.removeTask(item) },
                        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Text("Remove")
                    }
                }

            }
        }
        AddTaskForm(taskModel)
    }
}
@Composable
fun filterChecks(taskModel: TaskViewModel){
    var filterByDone: Boolean by remember {mutableStateOf(false)}
    var sortByDate: Boolean by remember {mutableStateOf(false)}
    Text(
        text = "Filters",
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
        modifier = Modifier.padding(vertical = 8.dp)
    )
    Row( verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = "Filter By Done",
            modifier = Modifier
                .weight(1f)
                .padding(start = 16.dp)
        )
        Checkbox(filterByDone, onCheckedChange = {
            filterByDone = !filterByDone
            taskModel.filterByDone(filterByDone)
        })
    }
    Row( verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = "Sort By Date",
            modifier = Modifier
                .weight(1f)
                .padding(start = 16.dp)
        )
        Checkbox(sortByDate, onCheckedChange = {
            sortByDate = !sortByDate
            taskModel.sortByDueDate(sortByDate)
        })
    }

        Button(onClick = {
            taskModel.resetFilter()
            sortByDate = false;
            filterByDone = false;
        }){
            Text(
                text = "Reset Filters",
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
        )
        }


}
@Composable
fun AddTaskForm(taskModel: TaskViewModel){
    var text by remember { mutableStateOf("") }
    var text2 by remember { mutableStateOf("") }

    Text(
        text = "Add New Task",
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
        modifier = Modifier.padding(vertical = 8.dp)
    )
    OutlinedTextField(
        value = text,
        onValueChange = { text = it },
        label = { Text("Title") },
        singleLine = true,
        modifier = Modifier.fillMaxWidth()
    )
    OutlinedTextField(
        value = text2,
        onValueChange = { text2 = it },
        label = { Text("Description") },
        singleLine = true,
        modifier = Modifier.fillMaxWidth()
    )
    Button(onClick = {
        if(text != ""){
            val newTask = Task(
                id = taskModel.taskList.value.size,
                title = text,
                description = text2,
                priority = 1,
                dueDate = LocalDate.now(),
                done = false
            )
            taskModel.addTask(newTask)
        }
    }) {
        Text("Add Task",
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth())
    }
}