package com.example.myapplication.viewmodel
import androidx.lifecycle.ViewModel
import com.example.myapplication.domain.Task
import com.example.myapplication.domain.mockTasks
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class TaskViewModel : ViewModel()
{
    private val _allTasks = MutableStateFlow<List<Task>>(emptyList())
    private val _visibleTasks = MutableStateFlow<List<Task>>(emptyList())
    val taskList: StateFlow<List<Task>> = _visibleTasks.asStateFlow()

    init {
        _allTasks.value = mockTasks
        _visibleTasks.value = mockTasks
    }
    fun addTask(task: Task) {
        _allTasks.value += task
        _visibleTasks.value = _allTasks.value
    }

    fun removeTask(task: Task) {
        _allTasks.value -= task
        _visibleTasks.value = _allTasks.value
    }

    fun resetFilter(){
        _visibleTasks.value = _allTasks.value
    }

    fun toggleDone(id: Int) {
        _allTasks.value = _allTasks.value.map { task ->
            if (task.id == id)
                task.copy(done = !task.done)
            else
                task
        }
        _visibleTasks.value = _allTasks.value
    }

    fun filterByDone(done: Boolean) {
        _visibleTasks.value = _allTasks.value.filter { it.done == done }
    }

    fun sortByDueDate(sort: Boolean) {
        if(sort)
            _visibleTasks.value = _visibleTasks.value.sortedBy { it.dueDate }
        else
            _visibleTasks.value = _visibleTasks.value.sortedByDescending { it.dueDate }
    }
}