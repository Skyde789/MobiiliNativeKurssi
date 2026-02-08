package com.example.myapplication.viewmodel
import TaskFilter
import TaskSorting
import TaskUIState
import androidx.lifecycle.ViewModel
import com.example.myapplication.model.Task
import com.example.myapplication.model.mockTasks
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.time.LocalDate

class TaskViewModel : ViewModel()
{
    private val _uiState = MutableStateFlow(TaskUIState())
    val uiState: StateFlow<TaskUIState> = _uiState.asStateFlow()

    init {
        _uiState.value = _uiState.value.copy( tasks = mockTasks)
    }

    fun setAddTaskDialogActive(open: Boolean){
        _uiState.value = _uiState.value.copy(
            addNewTask = open,
            newTaskDescription = "",
            newTaskTitle = "")
    }
    fun updateNewTaskTitle(text: String){
        _uiState.value = _uiState.value.copy(newTaskTitle = text);
    }
    fun updateNewTaskDescription(text: String){
        _uiState.value = _uiState.value.copy(newTaskDescription = text);
    }

    fun addTask() {
        val title = _uiState.value.newTaskTitle.trim()
        if (title.isEmpty()) return

        val newTask = Task(
            title = title,
            description = _uiState.value.newTaskDescription.trim(),
            date = LocalDate.now(),
            done = false
        )

        _uiState.value = _uiState.value.copy(
            tasks = _uiState.value.tasks + newTask,
            newTaskTitle = "",
            newTaskDescription = ""
        )

        setAddTaskDialogActive(false)
    }

    fun removeTask(id: String) {
        _uiState.value = _uiState.value.copy(
            tasks = _uiState.value.tasks.filter { it.id != id }
        )
    }

    fun toggleDone(id: String) {
        _uiState.value = _uiState.value.copy(
            tasks = _uiState.value.tasks.map { task ->
                if (task.id == id)
                    task.copy(done = !task.done)
                else
                    task
            }
        )
    }

    fun updateTask(updatedTask: Task) {
        _uiState.value = _uiState.value.copy(
            tasks = _uiState.value.tasks.map { task ->
                if (task.id == updatedTask.id)
                    updatedTask
                else
                    task
            }
        )
    }

    fun selectTask(id: String) {
        _uiState.value = _uiState.value.copy(
            selectedTask = _uiState.value.tasks.find { it.id == id }
        )
    }

    fun deselectTask(){
        _uiState.value = _uiState.value.copy(
            selectedTask = null
        )
    }

    fun setFilter(filter: TaskFilter){
        _uiState.value = _uiState.value.copy(filter = filter)
    }

    fun setSorting(sort: TaskSorting){
        _uiState.value = _uiState.value.copy(sort = sort)
    }

    fun getFilteredTasks(): List<Task> {
        val filtered = when (_uiState.value.filter) {
            TaskFilter.ALL -> _uiState.value.tasks
            TaskFilter.ACTIVE -> _uiState.value.tasks.filter { !it.done }
            TaskFilter.COMPLETED -> _uiState.value.tasks.filter { it.done }
        }

        return when (_uiState.value.sort) {
            TaskSorting.ASCENDING -> filtered.sortedBy { it.date }
            TaskSorting.DESCENDING -> filtered.sortedByDescending { it.date }
        }
    }
}