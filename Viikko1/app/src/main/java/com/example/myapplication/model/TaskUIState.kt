import com.example.myapplication.data.model.Task


data class TaskUIState(
    val tasks: List<Task> = emptyList(),
    val selectedTask: Task? = null,
    val filter: TaskFilter = TaskFilter.ALL,
    val sort: TaskSorting = TaskSorting.DESCENDING,
    val pendingCount: Int = 0
)

enum class TaskFilter {
    ALL, ACTIVE, COMPLETED
}

enum class TaskSorting{
    ASCENDING, DESCENDING
}