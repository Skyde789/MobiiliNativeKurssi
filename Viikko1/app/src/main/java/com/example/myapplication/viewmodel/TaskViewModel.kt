package com.example.myapplication.viewmodel
import TaskFilter
import TaskSorting
import TaskUIState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.model.Task
import com.example.myapplication.data.repository.TaskRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class TaskViewModel(private val repository: TaskRepository) : ViewModel()
{
    private val filter = MutableStateFlow(TaskFilter.ALL)
    private val sort = MutableStateFlow(TaskSorting.DESCENDING)
    private val selectedTaskId = MutableStateFlow<Int?>(null)
    private val _isAddDialogOpen = MutableStateFlow(false)
    val isAddDialogOpen: StateFlow<Boolean> = _isAddDialogOpen

    val uiState: StateFlow<TaskUIState> =
        combine(
            repository.allTasks,
            filter,
            sort,
            selectedTaskId,
            repository.pendingTaskCount
        ) { tasks, filter, sort, selectedTask, pendingTaskCount ->

            val filtered = when (filter) {
                TaskFilter.ALL -> tasks
                TaskFilter.ACTIVE -> tasks.filter { !it.isCompleted }
                TaskFilter.COMPLETED -> tasks.filter { it.isCompleted }
            }

            val sorted = when (sort) {
                TaskSorting.ASCENDING -> filtered.sortedBy { it.createdAt }
                TaskSorting.DESCENDING -> filtered.sortedByDescending { it.createdAt }
            }

            val selected = sorted.find { it.id == selectedTask }

            TaskUIState(
                tasks = sorted,
                selectedTask = selected,
                filter = filter,
                sort = sort,
                pendingCount = pendingTaskCount
            )
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            TaskUIState()
        )

    fun selectTask(task: Task) {
        selectedTaskId.value = task.id
    }

    fun deselectTask() {
        selectedTaskId.value = null
    }

    fun setFilter(value: TaskFilter) {
        filter.value = value
    }

    fun setSorting(value: TaskSorting) {
        sort.value = value
    }

    fun toggleDone(task: Task) {
        viewModelScope.launch {
            repository.update(task.copy(isCompleted = !task.isCompleted))
        }
    }

    fun addTask(title: String, description: String) {
        viewModelScope.launch {
            val task = Task(
                title = title,
                description = description
            )
            repository.insert(task)
        }
    }

    fun updateTask(title: String, description: String) {
        val current = uiState.value.selectedTask ?: return

        viewModelScope.launch {
            repository.update(current.copy(
                title = title,
                description = description
            ))
        }
    }

    fun deleteTask(task: Task) {
        viewModelScope.launch {
            repository.delete(task)
        }
    }

    fun deleteCompletedTasks() {
        viewModelScope.launch {
            repository.deleteCompletedTasks()
        }
    }

    fun openAddDialog() {
        _isAddDialogOpen.value = true
    }

    fun closeAddDialog() {
        _isAddDialogOpen.value = false
    }
}