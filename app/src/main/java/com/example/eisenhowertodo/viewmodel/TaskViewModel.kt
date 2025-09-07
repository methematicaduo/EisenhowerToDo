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
            // 🔴 Urgent & Important (Do it now)
            Task(
                title = "Prepare presentation",
                description = "Finish slides for tomorrow's meeting",
                category = TaskCategory.URGENT_IMPORTANT
            ),
            Task(
                title = "Submit urgent report",
                description = "Sent yesterday's deadline report",
                category = TaskCategory.URGENT_IMPORTANT,
                isCompleted = true
            ),
            
            // 🔵 Urgent & Unimportant (Delegate if possible)
            Task(
                title = "Make phone call",
                description = "Confirm delivery with courier",
                category = TaskCategory.URGENT_NOT_IMPORTANT
            ),
            Task(
                title = "Clean inbox",
                description = "Sorted and archived all emails",
                category = TaskCategory.URGENT_NOT_IMPORTANT,
                isCompleted = true
            ),
            
            // 🟡 Not Urgent & Important (Plan and schedule)
            Task(
                title = "Learn a new skill",
                description = "Start online course",
                category = TaskCategory.NOT_URGENT_IMPORTANT
            ),
            Task(
                title = "Finish book",
                description = "Completed last month's reading",
                category = TaskCategory.NOT_URGENT_IMPORTANT,
                isCompleted = true
            ),
            
            // 🟢 Not Urgent & Unimportant (Eliminate or limit)
            Task(
                title = "Play a game",
                description = "Try out the new console game",
                category = TaskCategory.NOT_URGENT_NOT_IMPORTANT
            ),
            Task(
                title = "Watch a movie",
                description = "Completed last weekend's film",
                category = TaskCategory.NOT_URGENT_NOT_IMPORTANT,
                isCompleted = true
            )
        )
    )
    val tasks: StateFlow<List<Task>> = _tasks.asStateFlow()

    private val _showAddTaskDialog = MutableStateFlow(false)
    val showAddTaskDialog: StateFlow<Boolean> = _showAddTaskDialog.asStateFlow()

    private val _focusedCategory = MutableStateFlow<TaskCategory?>(null)
    val focusedCategory: StateFlow<TaskCategory?> = _focusedCategory.asStateFlow()

    fun addTask(title: String, description: String, category: TaskCategory) {
        if (title.isNotBlank()) {
            val newTask = Task(
                title = title.trim(),
                description = description.trim(),
                category = category
            )
            _tasks.value = _tasks.value + newTask
        }
    }

    fun toggleTaskCompletion(taskId: String) {
        val currentTasks = _tasks.value.toMutableList()
        val taskIndex = currentTasks.indexOfFirst { it.id == taskId }
        
        if (taskIndex != -1) {
            val task = currentTasks[taskIndex]
            val updatedTask = task.copy(isCompleted = !task.isCompleted)
            currentTasks[taskIndex] = updatedTask
            _tasks.value = currentTasks
        }
    }

    fun deleteTask(taskId: String) {
        _tasks.value = _tasks.value.filter { it.id != taskId }
    }

    fun showAddTaskDialog() {
        _showAddTaskDialog.value = true
    }

    fun hideAddTaskDialog() {
        _showAddTaskDialog.value = false
    }

    fun getTasksByCategory(category: TaskCategory): List<Task> {
        val categoryTasks = _tasks.value.filter { it.category == category }
        // Sort tasks: incomplete first, then completed
        return categoryTasks.sortedWith(compareBy<Task> { it.isCompleted })
    }

    fun focusOnCategory(category: TaskCategory) {
        _focusedCategory.value = category
    }

    fun exitFocusMode() {
        _focusedCategory.value = null
    }
}
