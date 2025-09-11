package com.example.eisenhowertodo.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eisenhowertodo.model.Task
import com.example.eisenhowertodo.model.TaskCategory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TaskViewModel : ViewModel() {
    private val _tasks = MutableStateFlow<List<Task>>(
        listOf(
            Task(
                title = "Fix critical bug",
                description = "App crashes on startup",
                category = TaskCategory.URGENT_IMPORTANT
            ),
            Task(
                title = "Call client back",
                description = "Return phone call",
                category = TaskCategory.URGENT_NOT_IMPORTANT
            ),
            Task(
                title = "Plan vacation",
                description = "Research destinations",
                category = TaskCategory.NOT_URGENT_IMPORTANT
            ),
            Task(
                title = "Check social media",
                description = "Browse Facebook",
                category = TaskCategory.NOT_URGENT_NOT_IMPORTANT
            ),
            Task(
                title = "Deadline project",
                description = "Due tomorrow",
                category = TaskCategory.URGENT_IMPORTANT
            ),
            Task(
                title = "Organize desk",
                description = "Clean workspace",
                category = TaskCategory.NOT_URGENT_NOT_IMPORTANT
            )
        )
    )
    val tasks: StateFlow<List<Task>> = _tasks.asStateFlow()

    fun addTask(title: String, description: String, category: TaskCategory) {
        val newTask = Task(
            title = title,
            description = description,
            category = category
        )
        _tasks.value = _tasks.value + newTask
    }

    fun toggleTaskCompletion(taskId: String) {
        _tasks.value = _tasks.value.map { task ->
            if (task.id == taskId) {
                task.copy(isCompleted = !task.isCompleted)
            } else {
                task
            }
        }
    }

    fun deleteTask(taskId: String) {
        _tasks.value = _tasks.value.filter { it.id != taskId }
    }

    fun moveTask(taskId: String, newCategory: TaskCategory) {
        _tasks.value = _tasks.value.map { task ->
            if (task.id == taskId) {
                task.copy(category = newCategory)
            } else {
                task
            }
        }
    }

    fun getTasksByCategory(category: TaskCategory): List<Task> {
        return _tasks.value.filter { it.category == category }
    }
}
