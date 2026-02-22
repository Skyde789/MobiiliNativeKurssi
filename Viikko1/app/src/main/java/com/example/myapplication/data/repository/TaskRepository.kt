package com.example.myapplication.data.repository

// 📁 data/repository/TaskRepository.kt

import com.example.myapplication.data.local.TaskDao
import com.example.myapplication.data.model.Task
import kotlinx.coroutines.flow.Flow

// Repository ottaa DAO:n konstruktorin parametrina
// → Helppo testata: voidaan antaa mock-DAO testeissä
class TaskRepository(private val taskDao: TaskDao) {

    val allTasks: Flow<List<Task>> = taskDao.getAllTasks()
    val pendingTaskCount: Flow<Int> = taskDao.getPendingTaskCount()

    suspend fun insert(task: Task): Long {
        return taskDao.insert(task)
    }
    suspend fun getTaskById(id: Int): Task? {
        return taskDao.getTaskById(id);
    }
    suspend fun update(task: Task) {
        taskDao.update(task)
    }

    suspend fun delete(task: Task) {
        taskDao.delete(task)
    }

    // Yksinkertainen toggle: päivitä vain is_completed-sarake
    suspend fun toggleTaskStatus(taskId: Int, completed: Boolean) {
        taskDao.updateTaskStatus(taskId, completed)
    }

    fun getTasksByStatus(completed: Boolean): Flow<List<Task>> {
        return taskDao.getTasksByStatus(completed)
    }

    fun searchTasks(query: String): Flow<List<Task>> {
        return taskDao.searchTasks(query)
    }

    suspend fun deleteCompletedTasks() {
        taskDao.deleteCompletedTasks()
    }
}