import com.example.myapplication.model.Task


data class TaskUIState(
    val tasks: List<Task> = emptyList(),
    val selectedTask: Task? = null,
    val addNewTask: Boolean = false,
    val newTaskTitle: String = "",
    val newTaskDescription: String = "",
    val filter: TaskFilter = TaskFilter.ALL,
    val sort: TaskSorting = TaskSorting.DESCENDING
)

enum class TaskFilter {
    ALL, ACTIVE, COMPLETED
}

enum class TaskSorting{
    ASCENDING, DESCENDING
}