import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.ui.AddTaskForm
import com.example.myapplication.ui.DetailDialog
import com.example.myapplication.ui.FilterChecks
import com.example.myapplication.ui.TaskElement
import com.example.myapplication.viewmodel.TaskViewModel

@Composable
fun TaskScreen(modifier: Modifier, taskModel: TaskViewModel) {

    val uiState by taskModel.uiState.collectAsState()

    val filteredTasks = taskModel.getFilteredTasks()

    if(uiState.selectedTask != null)
    {
        uiState.selectedTask?.let { task ->
            DetailDialog(
                task = task,
                onClose = { taskModel.closeDialog() },
                onUpdate = { updatedTask -> taskModel.updateTask(updatedTask) },
                onDelete = { id -> taskModel.removeTask(id) }
            )
        }
    }

    Column(modifier = modifier
        .fillMaxSize()
        .padding(horizontal = 16.dp)){
        FilterChecks(taskModel)
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
        AddTaskForm(taskModel)
    }
}
